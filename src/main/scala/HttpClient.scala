import java.io.File
import java.net.URLConnection
import java.nio.file.{Paths, Files}
import scalaj.http.{Http, MultiPart}

/**
 * Created by mukel on 8/5/15.
 */
trait HttpClient {
  /**
   * request
   *
   * All of the complexity of the whole query system resides here.
   *
   * The parameters passed in [options] are handled as follows:
   *   (id, file: File)  file content is injected as multipart form data, MIME type is inferred form extension.
   *   (id, None)        is ignored (= the parameter is absent)
   *   (id, Some(x))     (given optional parameter) passed as parameter id=x.toString
   *   (id, x)           (anything else) passed as parameter id=x.toString
   */
  def request(requestUrl: String, options : (String, Any)*): String
}

