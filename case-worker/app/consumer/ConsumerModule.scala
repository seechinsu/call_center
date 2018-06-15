package consumer

import client.{ConsumerRecordProcessor, InputEvent}
import models.Case
import repositories.mongo.CaseRepository
import module.ConsumerAbstractModule
import reactivemongo.bson.BSONObjectID
import repositories.solr.SolrCaseRepository
import scala.concurrent.ExecutionContext

import javax.inject.Inject
import scala.concurrent.Future

class CaseAddedConsumerModule extends ConsumerAbstractModule {
  val name = "case-added"

  override def bindProcessor = {
    bind(classOf[ConsumerRecordProcessor]).to(classOf[MongoSolrCaseAddedETLProcessor])
  }
}

class MongoSolrCaseAddedETLProcessor @Inject()(solrRepository: SolrCaseRepository) (implicit ec: ExecutionContext) extends ConsumerRecordProcessor {
  override def processAsync(record: InputEvent): Future[Unit] = {
    serialize.deserialize[Case](record.value).map(c => solrRepository.addT(c._id.getOrElse(BSONObjectID.generate()), c)).getOrElse(Future.successful()).map(_ => ())
  }
}

class CaseUpdatedConsumerModule extends ConsumerAbstractModule {
  val name = "case-updated"

  override def bindProcessor = {
    bind(classOf[ConsumerRecordProcessor]).to(classOf[MongoSolrCaseUpdatedETLProcessor])
  }
}

class MongoSolrCaseUpdatedETLProcessor @Inject()(solrRepository: SolrCaseRepository) (implicit ec: ExecutionContext) extends ConsumerRecordProcessor {
  override def processAsync(record: InputEvent): Future[Unit] = {
    serialize.deserialize[Case](record.value).map(
      c => solrRepository.updateT(
        c._id.getOrElse(BSONObjectID.generate()),
        c
      )
    ).getOrElse(Future.successful()).map(_ => ())
  }
}

class CaseIdUpdatedConsumerModule extends ConsumerAbstractModule {
  val name = "case-id-updated"

  override def bindProcessor = {
    bind(classOf[ConsumerRecordProcessor]).to(classOf[MongoSolrCaseIdUpdatedETLProcessor])
  }
}

case class CaseId(id: String)

class MongoSolrCaseIdUpdatedETLProcessor @Inject()(
  solrRepository: SolrCaseRepository,
  caseRepository: CaseRepository
) (implicit ec: ExecutionContext) extends ConsumerRecordProcessor {
  override def processAsync(record: InputEvent): Future[Unit] = {
    serialize.deserialize[CaseId](record.value).map {
      cId =>
        val bsonId = BSONObjectID.parse(cId.id).getOrElse(BSONObjectID.generate())
        caseRepository.getT(bsonId).map {
          x =>
            x.foreach(
              mongoCase => solrRepository.updateT(
                bsonId,
                mongoCase
              )
            )
        }
    }.getOrElse(Future.successful())
  }
}


class CaseDeletedConsumerModule extends ConsumerAbstractModule {
  val name = "case-deleted"

  override def bindProcessor = {
    bind(classOf[ConsumerRecordProcessor]).to(classOf[MongoSolrCaseDeletedETLProcessor])
  }
}

class MongoSolrCaseDeletedETLProcessor @Inject()(solrRepository: SolrCaseRepository) (implicit ec: ExecutionContext) extends ConsumerRecordProcessor {
  override def processAsync(record: InputEvent): Future[Unit] = {
    serialize.deserialize[Case](record.value).map(
      c => solrRepository.deleteT(
        c._id.getOrElse(BSONObjectID.generate())
      )
    ).getOrElse(Future.successful()).map(_ => ())
  }
}
