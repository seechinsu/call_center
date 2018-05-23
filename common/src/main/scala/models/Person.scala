package models

import java.time.{LocalDate}
import reactivemongo.bson.{BSONObjectID}
import reactivemongo.play.json._


case class Person(_id: Option[BSONObjectID], personInfo: PersonInfo)

object Person {
  import play.api.libs.json._

  implicit val formats: OFormat[Person] = Json.format[Person]
}

case class PersonInfo(
                            personId: String,
                            personType: String,
                            firstName: String,
                            middleName: String,
                            lastName: String,
                            isCallerReluctant: Boolean,
                            refused: Boolean,
                            birthDate: LocalDate,
                            agency: String,
                            Sex: String
                            //contactInfo: ContactInfo,
                            //addressInfo: Seq[AddressInfo],
                            //notes: Seq[Narrative]
                          )

object PersonInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[PersonInfo] = Json.format[PersonInfo]
}