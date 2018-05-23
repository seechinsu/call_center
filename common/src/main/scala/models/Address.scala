package models

import reactivemongo.bson.{BSONObjectID}
import reactivemongo.play.json._


case class Address(_id: Option[BSONObjectID], addressInfo: AddressInfo)

object Address {
  import play.api.libs.json._

  implicit val formats: OFormat[Address] = Json.format[Address]
}

case class AddressInfo(
                            addressDescription: String,
                            addressType: String,
                            streetAddress: String,
                            city: String,
                            zip: String,
                            county: String,
                            state: String,
                            country: String
)

object AddressInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[AddressInfo] = Json.format[AddressInfo]
}

