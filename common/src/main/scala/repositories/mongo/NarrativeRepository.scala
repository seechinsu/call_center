package repositories.mongo

import javax.inject.Inject
import models.{Narrative}
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


class NarrativeRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import Narrative._

  val logger = LazyLogger.apply(this.getClass.getName)

  val narrativeCollection: Future[JSONCollection] = reactiveMongoApi.database.map{
    case x => x.collection("Narrative")
  }

  def getAll: Future[Seq[Narrative]] = {
    val query = Json.obj()
    narrativeCollection.flatMap{
      case collection =>
        val queryCollection = collection.find(query)
        val queryCursor = queryCollection.cursor[Narrative](ReadPreference.primary)
        queryCursor.collect[Seq](5000, Cursor.FailOnError[Seq[Narrative]]({case x => logger.error(x.toString())}))
    }
  }

  def addNarrative(narrative: Narrative): Future[WriteResult] = {
    narrativeCollection.flatMap(_.insert(narrative))
  }

  def deleteNarrative(id: BSONObjectID): Future[Option[Narrative]] = {
    val selector = BSONDocument("_id" -> id)
    narrativeCollection.flatMap(_.findAndRemove(selector).map(_.result[Narrative]))
  }

  def getNarrative(id: BSONObjectID): Future[Option[Narrative]] = {
    val query = BSONDocument("_id" -> id)
    narrativeCollection.flatMap(_.find(query).one[Narrative])
  }

}
