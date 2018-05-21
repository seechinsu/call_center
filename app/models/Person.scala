package models

import java.time.{Instant, LocalDate}

import javax.inject.Inject
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.util.LazyLogger

import scala.concurrent.{ExecutionContext, Future}


case class Person(_id: Option[BSONObjectID], personInfo: PersonInfo)

object Person {
  import play.api.libs.json._

  implicit val formats: OFormat[Person] = Json.format[Person]
}

case class PersonInfo(
                            caseId: String,
                            personType: String,
                            firstName: String,
                            middleName: String,
                            lastName: String,
                            isCallerReluctant: Boolean,
                            refused: Boolean,
                            phoneNumber: String,
                            birthDate: LocalDate,
                            Sex: String,
                            emailAddress: String,
                            agency: String,
                            streetAddress: String,
                            city: String,
                            zip: String,
                            county: String,
                            state: String,
                            country: String,
                            notes: String
                          )

object PersonInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[PersonInfo] = Json.format[PersonInfo]
}

class PersonRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import Person._
  import PersonInfo._
  val logger = LazyLogger.apply(this.getClass.getName)

  def personCollection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("Person"))

  def getAll: Future[Seq[Person]] = {
    val query = Json.obj()
    personCollection.flatMap{
      case collection =>
        val queryCollection = collection.find(query)
        val queryCursor = queryCollection.cursor[Person](ReadPreference.primary)
        queryCursor.collect[Seq](5000, Cursor.FailOnError[Seq[Person]]({case x => logger.error(x.toString())}))
    }
  }

  def add(person: Person): Future[WriteResult] = {
    personCollection.flatMap(_.insert(person))
  }

  def deletePersonInfo(id: BSONObjectID): Future[Option[Person]] = {
    val selector = BSONDocument("_id" -> id)
    personCollection.flatMap(_.findAndRemove(selector).map(_.result[Person]))
  }
}