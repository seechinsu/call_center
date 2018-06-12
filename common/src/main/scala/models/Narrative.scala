package models

import java.time.Instant
import reactivemongo.bson.{BSONObjectID}
import reactivemongo.play.json._


case class Narrative(_id: Option[BSONObjectID], narrativeInfo: NarrativeInfo)

object Narrative {
  import play.api.libs.json._

  implicit val formats: OFormat[Narrative] = Json.format[Narrative]
}

case class NarrativeInfo(
                          author: String,
                          narrtiveCreateDateTime: Long = Instant.now().toEpochMilli,
                          narrativeType: String,
                          narrativeText: String
                        )

object NarrativeInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[NarrativeInfo] = Json.format[NarrativeInfo]
}