package repositories.mongo

import models.Lead
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


class LeadRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import Lead._
  val logger = LazyLogger.apply(this.getClass.getName)

  val leadCollection: Future[JSONCollection] = reactiveMongoApi.database.map{
    case x => x.collection("Lead")
  }

  def getAllLeads: Future[Seq[Lead]] = {
    val query = Json.obj()
    leadCollection.flatMap{
      case collection =>
        val queryCollection = collection.find(query)
        val queryCursor = queryCollection.cursor[Lead](ReadPreference.primary)
        queryCursor.collect[Seq](5000, Cursor.FailOnError[Seq[Lead]]({case x => logger.error(x.toString())}))
    }
  }

  def addLead(lead: Lead): Future[WriteResult] = {
    leadCollection.flatMap(_.insert(lead))
  }

  def deleteLead(id: BSONObjectID): Future[Option[Lead]] = {
    val selector = BSONDocument("_id" -> id)
    leadCollection.flatMap(_.findAndRemove(selector).map(_.result[Lead]))
  }

  def getLead(id: BSONObjectID): Future[Option[Lead]] = {
    val query = BSONDocument("_id" -> id)
    leadCollection.flatMap(_.find(query).one[Lead])
  }
}


