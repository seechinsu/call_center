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
import reactivemongo.util.LazyLogger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}


class CaseRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import Case._
  val logger = LazyLogger.apply(this.getClass.getName)

  val caseCollection: Future[JSONCollection] = reactiveMongoApi.database.map{
    case x => x.collection("Case")
  }

  def getAllCases: Future[Seq[Case]] = {
    val query = Json.obj()
    caseCollection.flatMap{
      case collection =>
        val queryCollection = collection.find(query)
        val queryCursor = queryCollection.cursor[Case](ReadPreference.primary)
        queryCursor.collect[Seq](5000, Cursor.FailOnError[Seq[Case]]({case x => logger.error(x.toString())}))
    }
  }

  def addCase(`case`: Case): Future[WriteResult] = {
    caseCollection.flatMap(_.insert(`case`))
  }

  def deleteCase(id: BSONObjectID): Future[Option[Case]] = {
    val selector = BSONDocument("_id" -> id)
    caseCollection.flatMap(_.findAndRemove(selector).map(_.result[Case]))
  }

  def getCase(id: BSONObjectID): Future[Option[Case]] = {
    val query = BSONDocument("_id" -> id)
    caseCollection.flatMap(_.find(query).one[Case])
  }
}

