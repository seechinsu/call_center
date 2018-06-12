package repositories.mongo

import javax.inject.Inject
import models.ContactInfo
import play.modules.reactivemongo.ReactiveMongoApi
import scala.concurrent.{ExecutionContext}

class ContactInfoRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) extends BaseRepository[ContactInfo]("ContactInfo"){

}