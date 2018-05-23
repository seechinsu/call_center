package repositories.mongo

import models.Case
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.play.json.collection.JSONCollection

import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}


class CaseRepository @Inject() (implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import Case._

  def caseCollection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("cases"))

  def getAll: Future[Seq[Case]] = {
    val query = Json.obj()
    caseCollection.flatMap(
      _.find(query)
        .cursor[Case](ReadPreference.primary)
        .collect[Seq](100, Cursor.FailOnError[Seq[Case]]())
    )
  }

  def getCase(id: BSONObjectID): Future[Option[Case]] = {
    val query = BSONDocument("_id" -> id)
    caseCollection.flatMap(_.find(query).one[Case])
  }

  def addCase(`case`: Case): Future[WriteResult] = {
    caseCollection.flatMap(_.insert(`case`))
  }

  def updateCase(id: BSONObjectID, `case`: Case): Future[Option[Case]] = {
    val selector = BSONDocument("_id" -> id)
    caseCollection.flatMap(
      _.findAndUpdate(selector, `case`, fetchNewObject = true, upsert = true).map(_.result[Case])
    )
  }

  def deleteCase(id: BSONObjectID): Future[Option[Case]] = {
    val selector = BSONDocument("_id" -> id)
    caseCollection.flatMap(_.findAndRemove(selector).map(_.result[Case]))
  }

}
