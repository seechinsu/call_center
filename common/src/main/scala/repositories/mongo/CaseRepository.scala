package repositories.mongo

import models.Case
import play.modules.reactivemongo.ReactiveMongoApi
import javax.inject.Inject
import scala.concurrent.{ExecutionContext}


class CaseRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) extends BaseRepository[Case]("Case") {

}

