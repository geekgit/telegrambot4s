package info.mukel.telegrambot4s.api

import com.typesafe.scalalogging.Logger
import info.mukel.telegrambot4s.models.UpdateType.UpdateType
import info.mukel.telegrambot4s.models._

import scala.concurrent.Future

/** Bare-bones base interface for bots.
  *
  * The token must be declared as a non-val to avoid initialization issues.
  * Consider using a lazy val instead as following:
  *
  * {{
  *   lazy val token = Properties
  *     .envOrNone("BOT_TOKEN")
  *     .getOrElse(Source.fromFile("bot.token").getLines().mkString)
  * }}
  */
trait BotBase {
  def token: String
  protected val logger: Logger
  val client: RequestHandler

  def request: RequestHandler = client

  /**
    * Allowed updates. See [[info.mukel.telegrambot4s.models.UpdateType.Filters]].
    * By default all updates are allowed.
    *
    * @return Allowed updates. `None` indicates no-filtering (all updates allowed).
    *
    * {{{
    *   import UpdateType.Filters._
    *   override def allowedUpdates: Option[Seq[UpdateType]] =
    *     Some(MessageUpdates ++ InlineUpdates)
    * }}}
    */
  def allowedUpdates: Option[Seq[UpdateType]] = None

  /** Dispatch updates to specialized handlers.
    * Incoming update can be a message, edited message, channel post, edited channel post,
    * inline query, inline query results (sample), callback query, shipping or pre-checkout events.
    *
    * @param u Incoming update.
    */
  def receiveUpdate(u: Update): Unit = {
    u.message.foreach(receiveMessage)
    u.editedMessage.foreach(receiveEditedMessage)

    u.channelPost.foreach(receiveChannelPost)
    u.editedChannelPost.foreach(receiveEditedChannelPost)

    u.inlineQuery.foreach(receiveInlineQuery)
    u.chosenInlineResult.foreach(receiveChosenInlineResult)

    u.callbackQuery.foreach(receiveCallbackQuery)

    u.shippingQuery.foreach(receiveShippingQuery)
    u.preCheckoutQuery.foreach(receivePreCheckoutQuery)
  }

  def receiveMessage(message: Message): Unit = {}
  def receiveEditedMessage(editedMessage: Message): Unit = {}

  def receiveChannelPost(message: Message): Unit = {}
  def receiveEditedChannelPost(message: Message): Unit = {}

  def receiveInlineQuery(inlineQuery: InlineQuery): Unit = {}
  def receiveChosenInlineResult(chosenInlineResult: ChosenInlineResult): Unit = {}

  def receiveCallbackQuery(callbackQuery: CallbackQuery): Unit = {}

  def receiveShippingQuery(shippingQuery: ShippingQuery): Unit = {}
  def receivePreCheckoutQuery(preCheckoutQuery: PreCheckoutQuery): Unit = {}

  def run(): Unit = {}
  def shutdown(): Future[Unit] = { Future.successful(()) }
}
