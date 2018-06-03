package models

import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._


case class ContactInfo(_id: Option[BSONObjectID], contactInfoDetail: ContactInfoDetail)

object ContactInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[ContactInfo] = Json.format[ContactInfo]
}

case class ContactInfoDetail(
                              mobilePhones: Option[Seq[String]],
                              homePhones: Option[Seq[String]],
                              workPhones: Option[Seq[String]],
                              generalEmails: Option[Seq[String]],
                              generalPhones: Option[Seq[String]],
                              directPhones: Option[Seq[String]],
                              posterPhones: Option[Seq[String]],
                              faxPhones: Option[Seq[String]],
                              agencyEmails: Option[Seq[String]],
                              officerEmails: Option[Seq[String]]
                            )

object ContactInfoDetail {
  import play.api.libs.json._

  implicit val formats: OFormat[ContactInfoDetail] = Json.format[ContactInfoDetail]
}

