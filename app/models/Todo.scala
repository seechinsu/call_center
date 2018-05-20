package models

import javax.inject.Inject

import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.util.LazyLogger
import reactivemongo.util.LazyLogger.LazyLogger

import java.time.Instant
import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Riccardo Sirigu on 10/08/2017.
  */

case class Case(_id: Option[BSONObjectID], primaryCaseInfo: PrimaryCaseInfo)

object Case {
  import play.api.libs.json._

  implicit val formats: OFormat[Case] = Json.format[Case]
}

case class Todo(_id: Option[BSONObjectID], title: String, completed: Option[Boolean])

object JsonFormats {

  import play.api.libs.json._

  implicit val todoFormat: OFormat[Todo] = Json.format[Todo]
}

class TodoRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import JsonFormats._

  def todosCollection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("todos"))

  def getAll: Future[Seq[Todo]] = {
    val query = Json.obj()
    todosCollection.flatMap(
      _.find(query)
        .cursor[Todo](ReadPreference.primary)
        .collect[Seq](100, Cursor.FailOnError[Seq[Todo]]())
    )
  }

  def getTodo(id: BSONObjectID): Future[Option[Todo]] = {
    val query = BSONDocument("_id" -> id)
    todosCollection.flatMap(_.find(query).one[Todo])
  }

  def addTodo(todo: Todo): Future[WriteResult] = {
    todosCollection.flatMap(_.insert(todo))
  }

  def updateTodo(id: BSONObjectID, todo: Todo): Future[Option[Todo]] = {

    val selector = BSONDocument("_id" -> id)
    val updateModifier = BSONDocument(
      "$set" -> BSONDocument(
        "title" -> todo.title,
        "completed" -> todo.completed
      )
    )

    todosCollection.flatMap(
      _.findAndUpdate(selector, updateModifier, fetchNewObject = true).map(_.result[Todo])
    )
  }

  def deleteTodo(id: BSONObjectID): Future[Option[Todo]] = {
    val selector = BSONDocument("_id" -> id)
    todosCollection.flatMap(_.findAndRemove(selector).map(_.result[Todo]))
  }

}

case class PrimaryCaseInfo(
  caseType: String,
  requestType: String,
  operator: String,
  aniNumber: String,
  outOfRange: Boolean,
  source: String,
  timeStamp: Long = Instant.now().toEpochMilli,
  referredBy: String,
  relatedCases: Int,
  events: String,
  amberAlertFlag: Boolean,
  sourceOrganization: String,
  referredToAmeco: Boolean,
  amecoNpo: String
)

object PrimaryCaseInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[PrimaryCaseInfo] = Json.format[PrimaryCaseInfo]
}


class CaseRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import Case._
  import PrimaryCaseInfo._
  import reactivemongo.util.LazyLogger._
  val logger = LazyLogger.apply(this.getClass.getName)

  val caseCollection: Future[JSONCollection] = reactiveMongoApi.database.map{
    case x => x.collection("Case")
  }

  def getAll: Future[Seq[Case]] = {
    val query = Json.obj()
    caseCollection.flatMap{
      case collection =>
       val queryCollection = collection.find(query)
       val queryCursor = queryCollection.cursor[Case](ReadPreference.primary)
       queryCursor.collect[Seq](5000, Cursor.FailOnError[Seq[Case]]({case x => logger.error(x.toString())}))
    }
  }

  def add(`case`: Case): Future[WriteResult] = {
    caseCollection.flatMap(_.insert(`case`))
  }
}

