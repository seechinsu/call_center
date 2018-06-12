package repositories.mongo

import javax.inject.Inject
import models.{Address}
import play.modules.reactivemongo.ReactiveMongoApi

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext}


class AddressRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) extends BaseRepository[Address]("Address") {

}