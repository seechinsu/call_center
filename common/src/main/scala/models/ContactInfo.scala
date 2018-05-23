package models

import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._


case class ContactInfo(_id: Option[BSONObjectID], contactInfoDetail: ContactInfoDetail)

object ContactInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[ContactInfo] = Json.format[ContactInfo]
}

case class ContactInfoDetail(
                              _id: Option[BSONObjectID]
                              //mobilePhones: Seq[String],
                              //homePhones: Seq[String],
                              //workPhones: Seq[String],
                              //emails: Seq[String]
                            )

object ContactInfoDetail {
  import play.api.libs.json._

  implicit val formats: OFormat[ContactInfoDetail] = Json.format[ContactInfoDetail]
}

