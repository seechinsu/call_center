package repositories.mongo

import javax.inject.Inject
import models.{Narrative}
import play.modules.reactivemongo.ReactiveMongoApi
import scala.concurrent.{ExecutionContext}


class NarrativeRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) extends BaseRepository[Narrative]("Narrative"){

}
