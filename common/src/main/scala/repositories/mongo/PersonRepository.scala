package repositories.mongo

import models.Person
import play.modules.reactivemongo.ReactiveMongoApi
import javax.inject.Inject
import scala.concurrent.{ExecutionContext}

class PersonRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) extends BaseRepository[Person]("Person"){

}