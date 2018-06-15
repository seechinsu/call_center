package models

import java.time.{LocalDate}
import reactivemongo.bson.{BSONObjectID}
import reactivemongo.play.json._

case class LawEnforcement(_id: Option[BSONObjectID] = Some(BSONObjectID.generate()), lawEnforcementInfo: LawEnforcementInfo)

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
                               ocaNumber: String,
                               oriNumber: String,
                               childIssuingAgency: String,
                               contactInfo: Seq[ContactInfo],
                               addressInfo: Seq[Address],
                               notes: Seq[Narrative]
                             )

object LawEnforcementInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[LawEnforcementInfo] = Json.format[LawEnforcementInfo]
}
