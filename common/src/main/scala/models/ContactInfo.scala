package models

import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._


case class ContactInfo(_id: Option[BSONObjectID] = Some(BSONObjectID.generate()), contactInfoDetail: ContactInfoDetail)

object ContactInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[ContactInfo] = Json.format[ContactInfo]
}

case class ContactInfoDetail(
                              phoneNumbers: Seq[PhoneNumber],
                              emailAddresses: Seq[EmailsAddress],
                              onlineHandles: Seq[OnlineHandle]
                            )

object ContactInfoDetail {
  import play.api.libs.json._

  implicit val formats: OFormat[ContactInfoDetail] = Json.format[ContactInfoDetail]
}

case class PhoneNumber(
                       phoneNumberDeviceType: String,
                       phoneNumberType: String,
                       phoneCountryCode: Int,
                       phoneAreaCode: Int,
                       phoneNumber: Int
                       )

object PhoneNumber {
  import play.api.libs.json._

  implicit val formats: OFormat[PhoneNumber] = Json.format[PhoneNumber]
}

case class EmailsAddress(
                         emailAddressType: String,
                         emailCompany: String,
                         emailAddress: String
                       )

object EmailsAddress {
  import play.api.libs.json._

  implicit val formats: OFormat[EmailsAddress] = Json.format[EmailsAddress]
}

case class OnlineHandle(
                          onlineHandle: String,
                          company: String
                        )

object OnlineHandle {
  import play.api.libs.json._

  implicit val formats: OFormat[OnlineHandle] = Json.format[OnlineHandle]
}
