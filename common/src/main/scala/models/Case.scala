package models

import java.time.Instant
import reactivemongo.bson.{BSONObjectID}
import reactivemongo.play.json._


case class Case(_id: Option[BSONObjectID], primaryCaseInfo: PrimaryCaseInfo)

object Case {
  import play.api.libs.json._

  implicit val formats: OFormat[Case] = Json.format[Case]
}

case class PrimaryCaseInfo(
                            caseType: String,
                            requestType: String,
                            operator: String,
                            dateOfIntake: Long = Instant.now().toEpochMilli,
                            aniNumber: Option[String],
                            outOfRange: Option[Boolean],
                            source: String,
                            referredBy: Option[String],
                            relatedCases: Option[Int],
                            events: Option[String],
                            amberAlertFlag: Boolean,
                            sourceOrganization: Option[String],
                            referredToAmeco: Boolean,
                            amecoNpo: Option[String],
                            lead: Option[Seq[Lead]],
                            lawEnforcementInfo: Option[Seq[LawEnforcement]],
                            vehicle: Option[Seq[Vehicle]],
                            narrative: Option[Seq[Narrative]],
                            people: Option[Seq[Person]]

)

object PrimaryCaseInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[PrimaryCaseInfo] = Json.format[PrimaryCaseInfo]
}