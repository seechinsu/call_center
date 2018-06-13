package service

import anorm._

import com.typesafe.scalalogging.LazyLogging
import play.api.Configuration
import play.api.db.{Database, NamedDatabase}

import java.sql.Connection
import java.util.{Date, UUID}
import javax.inject.Inject

trait QueueService {
  def cleanup(): Unit

  def updateStatus(id: String, status: String): Unit

  def dequeue(): (Boolean, String, String, String, String)

  def getJob(): Option[QueueTableData]
  def updateJob(tableData: QueueTableData): Int

  def enqueue(service: String, startDate: String, endDate: String): Unit
}

class QueueServiceImpl @Inject()(
  @NamedDatabase("mysql") db: Database,
  configuration: Configuration
) extends QueueService with LazyLogging {
  val lockTimeout = configuration.getString("lockTimeout").getOrElse("5 hour")
  val simpleDateFormatter = SchedulerUtils.simpleDateFormatter
  val queueTableParser = QueueTableData.queueTableParser

  override def enqueue(
    service: String,
    startDate: String,
    endDate: String
  ): Unit = try {
    db.withConnection { implicit conn =>
      try {
        val start = simpleDateFormatter.parse(startDate)
        val end = simpleDateFormatter.parse(endDate)
        SchedulerUtils.generateDateRange(start, end, simpleDateFormatter).foreach { case (st, en) =>
          insertSQL(service, st, en)
        }
      } catch {
        case e1: Exception =>
          logger.error("failed to parse and insert", e1)
          insertSQL(service, startDate, endDate)
      }
    }
  } catch {
    case e: Exception => logger.error("failed to enqueue", e)
  }

  private def insertSQL(
    service: String,
    startDate: String,
    endDate: String
  )(implicit conn: Connection) = {
    logger.info(s"about to insert $service, $startDate, $endDate")
    SQL""" INSERT INTO QUEUE_TABLE ( SERVICE, start, end, id) VALUES ($service, $startDate, $endDate, ${UUID.randomUUID().toString})""".executeInsert()
  }

  override def getJob(): Option[QueueTableData] = {
    val servicesWorking = db.withConnection(
      implicit conn =>
        SQL"""
         SELECT * FROM QUEUE_TABLE WHERE STATUS = 'Working' And now() < DATE_ADD(timeout, INTERVAL #${lockTimeout})
      """.executeQuery().as(queueTableParser.*)
    )
    logger.info(s"working services: $servicesWorking")
    servicesWorking.headOption match {
      case None => val servicesToBeRun = db.withConnection(
        implicit conn =>
          SQL"""
         SELECT * FROM QUEUE_TABLE WHERE STATUS = 'Submitted' ORDER BY start DESC LIMIT 1
        """.executeQuery().as(queueTableParser.*)
      )
        logger.info(s"to be run services: $servicesToBeRun")
        servicesToBeRun.headOption
      case _ => None
    }
  }

  override def updateJob(queueTable: QueueTableData): Int = {
    db.withConnection { implicit conn =>
      val x =
        SQL"""
              update QUEUE_TABLE set STATUS = 'Working', timeout = now()
              where STATUS = 'Submitted' AND id = ${queueTable.id}
        """.executeUpdate()
      if (x > 0) {
        logger.info(s"services updated: ${queueTable.service}, ${queueTable.start}, ${queueTable.end}, ${queueTable.id}")
        x
      } else {
        logger.info(s"services NOT updated: ${queueTable.service}, ${queueTable.start}, ${queueTable.end}, ${queueTable.id}")
        x
      }
    }
  }

  override def dequeue(): (Boolean, String, String, String, String) = {
    val falseResult = (false, "", "", "", "")
    val job = getJob()
    job.map{x =>
      val result = updateJob(x)
      x.withUpdated(result)
    }.getOrElse(falseResult)
  }

  override def cleanup(): Unit = try {
    db.withConnection(
      implicit conn =>
        SQL"""
         UPDATE QUEUE_TABLE SET STATUS = 'Done' WHERE STATUS = 'Cleared'
      """.executeInsert()
    )
    db.withConnection(
      implicit conn =>
        SQL"""
         UPDATE QUEUE_TABLE SET STATUS = 'Timeout' WHERE now() > DATE_ADD(timeout, INTERVAL #${lockTimeout}) and STATUS = 'Working'
      """.executeInsert()
    )
  } catch {
    case e: Exception => logger.error("failed to cleanup", e)
  }

  override def updateStatus(
    id: String,
    status: String
  ): Unit = try {
    db.withConnection(
      implicit conn =>
        SQL"""
         Update QUEUE_TABLE set STATUS = $status WHERE id = $id
      """.execute()
    )
  } catch {
    case e: Exception => logger.error("failed to update status", e)
  }
}

case class QueueTableData(id: String, service: String, start: String, end: String, status: String, timeout: Date){
  def withUpdated(isUpdated: Int): (Boolean, String, String, String, String) = {
    (if(isUpdated > 0) true else false, service, start, end, id)
  }
}
object QueueTableData {
  val queueTableParser: RowParser[QueueTableData] = Macro.namedParser[QueueTableData]
}
