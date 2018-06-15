package models

import java.time.{LocalDate}
import reactivemongo.bson.{BSONObjectID}
import reactivemongo.play.json._

case class Person(
                   _id: Option[BSONObjectID] = Some(BSONObjectID.generate()),
                   firstName: String,
                   middleName: String,
                   lastName: String,
                   aliasName: Seq[String],
                   nickname: Seq[String],
                   birthDate: LocalDate,
                   race: Seq[String],
                   gender: String,
                   personGeneralInfo: PersonGeneralInfo,
                   personMissingInfo: PersonMissingInfo,
                   personIdentificationInfo: PersonIdentificationInfo,
                   personDescriptiveInfo: PersonDescriptiveInfo,
                   personEndangerments: Seq[PersonEndangerments]
                 )

object Person {

  import play.api.libs.json._

  implicit val formats: OFormat[Person] = Json.format[Person]
}

case class PersonGeneralInfo(
                      personType: Seq[String],
                      multiRacialDescription: String,
                      americanIndianTribe: String,
                      isCallerReluctant: Boolean,
                      refused: Boolean,
                      schoolAttended: String,
                      lastGradeAttended: String,
                      countryOfBirth: String,
                      countryOfResidence: String,
                      childCaseType: String,
                      childStatus: String,
                      notifyCyberTip: Boolean,
                      agency: String,
                      contactInfo: Seq[ContactInfo],
                      notes: Seq[Narrative]
                     )

object PersonGeneralInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[PersonGeneralInfo] = Json.format[PersonGeneralInfo]
}

case class PersonMissingInfo(
                              interpolNotice: Boolean,
                              altLeaCaseNumber: String,
                              activityPriorToMissing: String,
                              ncicInfo: Seq[NcicInfo],
                              addressInfo: Seq[Address]
                             )

object PersonMissingInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[PersonMissingInfo] = Json.format[PersonMissingInfo]
}

case class NcicInfo(
                   ncicNumber: String,
                   ncicDate: LocalDate,
                   ncicAtIntake: Boolean,
                   ncicDollarEight: Boolean,
                   ncicCaFlag: Boolean,
                   ncicDentalRecords: Boolean
                   )

object NcicInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[NcicInfo] = Json.format[NcicInfo]
}

case class PersonIdentificationInfo(
                                     passportNumber: String,
                                     passportIssuedDate: LocalDate,
                                     ssn: String,
                                     driversLicenseNumber: String
                                   )

object PersonIdentificationInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[PersonIdentificationInfo] = Json.format[PersonIdentificationInfo]
}

case class PersonDescriptiveInfo(
                                  physical: Seq[PhysicalDesc],
                                  scarsMarks: Seq[ScarsMarksTattoos],
                                  clothing: Seq[Clothing]
                                  )

object PersonDescriptiveInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[PersonDescriptiveInfo] = Json.format[PersonDescriptiveInfo]
}

case class PhysicalDesc(
                       attribute: String,
                       value: String,
                       notes: String
                       )

object PhysicalDesc {
  import play.api.libs.json._

  implicit val formats: OFormat[PhysicalDesc] = Json.format[PhysicalDesc]
}

case class ScarsMarksTattoos(
                         attribute: String,
                         value: String,
                         notes: String
                       )

object ScarsMarksTattoos {
  import play.api.libs.json._

  implicit val formats: OFormat[ScarsMarksTattoos] = Json.format[ScarsMarksTattoos]
}

case class Clothing(
                              attribute: String,
                              value: String,
                              notes: String
                            )

object Clothing {
  import play.api.libs.json._

  implicit val formats: OFormat[Clothing] = Json.format[Clothing]
}

case class PersonEndangerments(
                     attribute: String,
                     value: String,
                     notes: String
                   )

object PersonEndangerments {
  import play.api.libs.json._

  implicit val formats: OFormat[PersonEndangerments] = Json.format[PersonEndangerments]
}
