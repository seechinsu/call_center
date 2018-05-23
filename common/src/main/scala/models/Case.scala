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
                            _id: Option[BSONObjectID],
                            caseType: String,
                            requestType: String,
                            operator: String,
                            dateOfCall: Long = Instant.now().toEpochMilli,
                            aniNumber: String,
                            outOfRange: Boolean,
                            source: String,
                            referredBy: String,
                            relatedCases: Int,
                            events: String,
                            amberAlertFlag: Boolean,
                            sourceOrganization: String,
                            referredToAmeco: Boolean,
                            amecoNpo: String
                            //lawEnforcementInfo: Seq[LawEnforcement],
                            //vehicle: Seq[Vehicle],
                            //narrative: Seq[Narrative],
                            //people: Seq[Person]
)

object PrimaryCaseInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[PrimaryCaseInfo] = Json.format[PrimaryCaseInfo]
}