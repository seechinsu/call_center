package models

import java.time.Instant
import reactivemongo.bson.{BSONObjectID}
import reactivemongo.play.json._


case class Lead(_id: Option[BSONObjectID], primaryLeadInfo: PrimaryLeadInfo)

object Lead {
  import play.api.libs.json._

  implicit val formats: OFormat[Lead] = Json.format[Lead]
}

case class PrimaryLeadInfo(
                            inFile: Boolean,
                            leadType: String,
                            operator: String,
                            dateOfLead: Long = Instant.now().toEpochMilli,
                            aniNumber: Option[String],
                            source: String,
                            lawEnforcementInfo: Option[Seq[LawEnforcement]],
                            vehicle: Option[Seq[Vehicle]],
                            narrative: Option[Seq[Narrative]],
                            people: Option[Seq[Person]]
                          )

object PrimaryLeadInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[PrimaryLeadInfo] = Json.format[PrimaryLeadInfo]
}