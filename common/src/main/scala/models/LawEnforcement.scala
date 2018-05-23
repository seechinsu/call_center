package models

import java.time.{LocalDate}
import reactivemongo.bson.{BSONObjectID}
import reactivemongo.play.json._


case class LawEnforcement(_id: Option[BSONObjectID], lawEnforcementInfo: LawEnforcementInfo)

object LawEnforcement {

  import play.api.libs.json._

  implicit val formats: OFormat[LawEnforcement] = Json.format[LawEnforcement]
}

case class LawEnforcementInfo(
                       dateReported: LocalDate,
                       assignedOfficer: String,
                       respondingOfficer: String,
                       department: String,
                       unit: String,
                       signedMediaWaiver: Boolean,
                       receiveLeads: Boolean,
                       //mobilePhone: Seq[String],
                       //generalPhone: Seq[String],
                       //directPhone: Seq[String],
                       //posterPhone: Seq[String],
                       //faxPhone: Seq[String],
                       //agencyEmail: Seq[String],
                       //officerEmail: Seq[String],
                       ocaNumber: String,
                       oriNumber: String,
                       childIssuingAgency: String,
                       streetAddress: String,
                       city: String,
                       zip: String,
                       county: String,
                       state: String,
                       country: String,
                       notes: String
                     )

object LawEnforcementInfo {

  import play.api.libs.json._

  implicit val formats: OFormat[LawEnforcementInfo] = Json.format[LawEnforcementInfo]
}