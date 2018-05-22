package models

import play.api.libs.json.Json
import reactivemongo.bson.{BSONDateTime, BSONDocument, BSONObjectID}
import reactivemongo.play.json._

case class Vehicle(
  year: String,
  make: String,
  model: String,
  vehicleStyle: String,
  vehicleColor: String,
  vehicleTagNumber: String,
  vehicleTagYear: String,
  vehicleTagState: String,
  vinNumber: String,
  notes: String
)
object Vehicle {
  import play.api.libs.json._
  implicit val format: OFormat[Vehicle] = Json.format[Vehicle]
}
case class Narrative(
  author: String,
  narrtiveDate: BSONDateTime,
  narrativeType: String,
  narrativeText: String
)
object Narrative {
  import play.api.libs.json._
  implicit val format: OFormat[Narrative] = Json.format[Narrative]
}
case class ContactInfo(
  mobilePhones: Seq[String],
  homePhones: Seq[String],
  workPhones: Seq[String],
  emails: Seq[String]
)
object ContactInfo {
  import play.api.libs.json._
  implicit val format: OFormat[ContactInfo] = Json.format[ContactInfo]
}
case class AddresseInfo(
  addressDescription: String,
  addressType: String,
  streetAddress: String,
  city: String,
  zip: String,
  county: String,
  state: String,
  country: String
)
object AddresseInfo {
  import play.api.libs.json._
  implicit val format: OFormat[AddresseInfo] = Json.format[AddresseInfo]
}
case class Person(
  personId: Option[BSONObjectID] =  Some(BSONObjectID.generate()),
  personType: String,
  firstName: String,
  middleName: String,
  lastName: String,
  contactInfo: ContactInfo,
  addresseInfo: Seq[AddresseInfo]
)
object Person {
  import play.api.libs.json._
  implicit val format: OFormat[Person] = Json.format[Person]
}
case class Case(
  _id: Option[BSONObjectID],
  caseType: String,
  requestType: String,
  operator: String,
  dateOfCall: BSONDateTime,
  lawEnforcementInfo: String,
  vehicle: Seq[Vehicle],
  narrative: Seq[Narrative],
  people: Seq[Person]
)
object Case {
  import play.api.libs.json._
  implicit val format: OFormat[Case] = Json.format[Case]
}
