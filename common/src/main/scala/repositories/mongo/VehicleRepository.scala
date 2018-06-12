package repositories.mongo

import javax.inject.Inject
import models.{Vehicle}
import play.modules.reactivemongo.ReactiveMongoApi
import scala.concurrent.{ExecutionContext}


class VehicleRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) extends BaseRepository[Vehicle]("Vehicle") {

}