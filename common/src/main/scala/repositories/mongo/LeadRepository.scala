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


class LeadRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) extends BaseRepository[Lead](ec, reactiveMongoApi, "Lead")


