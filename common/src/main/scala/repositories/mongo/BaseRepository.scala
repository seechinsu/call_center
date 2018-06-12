package repositories.mongo

import reactivemongo.api.commands.{UpdateWriteResult, WriteResult}
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.play.json.collection.JSONCollection

import play.api.libs.json.{Json, OFormat}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import play.api.libs.json.{Json, OFormat}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._

import scala.reflect.Manifest
import reactivemongo.util.LazyLogger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

abstract class BaseRepository[T <: Product : Manifest : OFormat](name: String)(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {
  val logger = LazyLogger.apply(this.getClass.getName)

  val collection: Future[JSONCollection] = reactiveMongoApi.database.map{
    case x => x.collection(name)
  }

  def getAllTs: Future[Seq[T]] = {
    val query = Json.obj()
    collection.flatMap{
      case collection =>
        val queryCollection = collection.find(query)
        val queryCursor = queryCollection.cursor[T](ReadPreference.primary)
        queryCursor.collect[Seq](5000, Cursor.FailOnError[Seq[T]]({case x => logger.error(x.toString())}))
    }
  }

  def addT(`T`: T): Future[WriteResult] = {
    collection.flatMap(_.insert(`T`))
  }

  def deleteT(id: BSONObjectID): Future[Option[T]] = {
    val selector = BSONDocument("_id" -> id)
    collection.flatMap(_.findAndRemove(selector).map(_.result[T]))
  }

  def getT(id: BSONObjectID): Future[Option[T]] = {
    val query = BSONDocument("_id" -> id)
    collection.flatMap(_.find(query).one[T])
  }

  //TODO: figure out reflection oriented merge
  def mergeThing(result: T, source: T)(implicit manifest: Manifest[T]): T = {
    manifest.runtimeClass.getDeclaredFields.map(_.getName)
    for(values <- source.productIterator)
      values
    source
  }

  def update(id: BSONObjectID, product: T): Future[UpdateWriteResult] = {
    val selector = BSONDocument("_id" -> id)
    for{
      thing <- getT(id)
      realThing = if(thing.isDefined) thing.get else throw new RuntimeException(s"unable to update thing $id")
      writes <- collection.flatMap(_.update(selector, mergeThing(realThing, product), upsert = true ))
    } yield (writes)
  }
}
