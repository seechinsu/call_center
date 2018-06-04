package repositories.mongo

import models.{LawEnforcement}
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.play.json.collection.JSONCollection
import javax.inject.Inject
import reactivemongo.util.LazyLogger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

class LawEnforcementRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import LawEnforcement._
  val logger = LazyLogger.apply(this.getClass.getName)

  val lawEnforcementCollection: Future[JSONCollection] = reactiveMongoApi.database.map{
    case x => x.collection("LawEnforcement")
  }

  def getAll: Future[Seq[LawEnforcement]] = {
    val query = Json.obj()
    lawEnforcementCollection.flatMap{
      case collection =>
        val queryCollection = collection.find(query)
        val queryCursor = queryCollection.cursor[LawEnforcement](ReadPreference.primary)
        queryCursor.collect[Seq](5000, Cursor.FailOnError[Seq[LawEnforcement]]({case x => logger.error(x.toString())}))
    }
  }

  def addLawEnforcement(lawEnforcement: LawEnforcement): Future[WriteResult] = {
    lawEnforcementCollection.flatMap(_.insert(lawEnforcement))
  }

  def deleteLawEnforcement(id: BSONObjectID): Future[Option[LawEnforcement]] = {
    val selector = BSONDocument("_id" -> id)
    lawEnforcementCollection.flatMap(_.findAndRemove(selector).map(_.result[LawEnforcement]))
  }

  def getLawEnforcement(id: BSONObjectID): Future[Option[LawEnforcement]] = {
    val query = BSONDocument("_id" -> id)
    lawEnforcementCollection.flatMap(_.find(query).one[LawEnforcement])
  }
}