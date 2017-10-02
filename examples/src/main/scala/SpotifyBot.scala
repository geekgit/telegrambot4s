import java.net.URLEncoder

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.{Authorization, BasicHttpCredentials}
import akka.http.scaladsl.unmarshalling.Unmarshal
import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models._
import org.json4s._
import org.json4s.jackson.JsonMethods._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Spotify search and play previews.
  * Usage: @YourAwesomeBot query
  *
  * The provided clientId/secret are not guaranteed to work forever.
  * See [[https://developer.spotify.com/web-api/authorization-guide/]]
  */
class SpotifyBot(token: String, spotify_clientId: String = "YourClientID", spotify_secretId: String = "YourSecretID") extends ExampleBot(token) with Polling {

  val limit = 10

  val accessToken = {

    val clientId = spotify_clientId
    val secret = spotify_secretId

    val authRequest = HttpRequest(
      HttpMethods.POST,
      Uri("https://accounts.spotify.com/api/token"),
      List(Authorization(BasicHttpCredentials(clientId, secret))),
      FormData("grant_type" -> "client_credentials").toEntity
    )

    val f = for {
      response <- Http().singleRequest(authRequest)
      if response.status.isSuccess()
      jsonText <- Unmarshal(response).to[String]
    } yield {
      val JString(token) = parse(jsonText) \ "access_token"
      logger.info(s"Spotify AccessToken: $token")
      token
    }

    Await.result(f, Duration.Inf)
  }

  override def receiveInlineQuery(inlineQuery: InlineQuery): Unit = {
    super.receiveInlineQuery(inlineQuery)
    val query = inlineQuery.query
    val offset = Extractors.Int.unapply(inlineQuery.offset).getOrElse(0)

    val url = s"https://api.spotify.com/v1/search?access_token=$accessToken&type=track&limit=$limit&offset=$offset&q=${URLEncoder.encode(query, "UTF-8")}"

    for {
      response <- Http().singleRequest(HttpRequest(uri = Uri(url)))
      if response.status.isSuccess()
      jsonText <- Unmarshal(response).to[String]
    } yield {

      val results = for {
        JArray(tracks) <- (parse(jsonText) \ "tracks" \ "items")
        trackObj <- tracks
        JObject(track) <- trackObj
        JField("id", JString(id)) <- track
        JField("name", JString(title)) <- track
        JField("preview_url", JString(preview_url)) <- track
        JString(artist) <- ((trackObj \ "artists") (0) \ "name")
      } yield
        InlineQueryResultAudio(id, preview_url, title, artist, audioDuration = 30)

      request(
        AnswerInlineQuery(
          inlineQuery.id,
          results,
          nextOffset = (offset + limit).toString
        )
      )
    }
  }
}
