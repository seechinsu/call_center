package models

import play.api.libs.functional.syntax._
import play.api.libs.json.JsPath
import play.api.libs.json.Reads._

case class LoadFileCommand(timestamp: Long, fileName: String) {
  require(timestamp > 0)
}

object LoadFileCommand {

  implicit val LoadFileCommandFormat = (
    (JsPath \ "timestamp").format[Long](min(0L)) and
      (JsPath \ "filename").format[String]
    ) (LoadFileCommand.apply, unlift(LoadFileCommand.unapply))

}
