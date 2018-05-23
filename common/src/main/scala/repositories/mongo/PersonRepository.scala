package repositories.mongo

import javax.inject.Inject
import models.{Person}
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.util.LazyLogger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}


class PersonRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import Person._
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

  def addPerson(person: Person): Future[WriteResult] = {
    personCollection.flatMap(_.insert(person))
  }

  def deletePerson(id: BSONObjectID): Future[Option[Person]] = {
    val selector = BSONDocument("_id" -> id)
    personCollection.flatMap(_.findAndRemove(selector).map(_.result[Person]))
  }

  def getPerson(id: BSONObjectID): Future[Option[Person]] = {
    val query = BSONDocument("_id" -> id)
    personCollection.flatMap(_.find(query).one[Person])
  }
}