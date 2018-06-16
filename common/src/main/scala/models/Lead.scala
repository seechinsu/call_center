package models

import java.time.Instant
import java.time.{LocalDate}
import reactivemongo.bson.{BSONObjectID}
import reactivemongo.play.json._


case class Lead(_id: BSONObjectID,
                inFile: Boolean,
                leadType: String,
                operator: String,
                dateOfLead: Long = Instant.now().toEpochMilli,
                dateLastSeen: LocalDate,
                primaryLeadInfo: PrimaryLeadInfo)

object Lead {
  import play.api.libs.json._

  implicit val formats: OFormat[Lead] = Json.format[Lead]
}

case class PrimaryLeadInfo(
                            priorSighting: Boolean,
                            willSeeAgain: Boolean,
                            childNotSeen: Boolean,
                            sightingLocation: Seq[Address],
                            aniNumber: Option[String],
                            source: String,
                            lawEnforcementInfo: Seq[LawEnforcement],
                            vehicle: Seq[Vehicle],
                            narrative: Seq[Narrative],
                            people: Seq[Person]
                          )

object PrimaryLeadInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[PrimaryLeadInfo] = Json.format[PrimaryLeadInfo]
}
