package service

import repositories.{PrestoStore, WarehouseStore}

import com.typesafe.scalalogging.LazyLogging
import org.joda.time.{DateTime, Days}

import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

trait DataPumpService {
  def pump(startDate: Date): Unit
  def pump(entityType: String): Unit = ???
  def manualPump( entityType: String, start: Date, end: Date ): Unit = ???
}

class DataPumpServiceImpl @Inject()(
  prestoStore: PrestoStore,
  warehouseStore: WarehouseStore
)
  extends DataPumpService with LazyLogging {

  val TWENTY_FOUR_HOUR_PERIOD = (24 * 3600 * 1000)

//  override def pump(entityType: String): Unit = {
//
//    val lastSyncTime = syncStateDao.getLastSyncTime(entityType).getOrElse(SchedulerUtils.getStartTime(new Date(), -24))
//    val currentTime = System.currentTimeMillis()
//    try {
//      logger.info(s"starting to pump data for $entityType from $lastSyncTime")
//      val auditAccessEvents = eventStore.select(entityType, lastSyncTime)
//      val finishedPumpTime = System.currentTimeMillis()
//      logger.info(s"ending pump, took: ${finishedPumpTime - currentTime} millis")
//      auditAccessEvents.foreach { event =>
//        try{
//          logger.debug(s"Audit access event is $event")
//          tableStore.insert(event)
//        }
//        catch {
//          case e: Exception => logger.error(s"Failed to insert $event", e)
//        }
//      }
//      val finishedInsertTime = System.currentTimeMillis()
//      logger.info(s"ending insert into audit.trail in dynamo, took: ${finishedInsertTime - finishedPumpTime} millis")
//      syncStateDao.saveLatestSyncTime(entityType, new Date(lastSyncTime.getTime() + TWENTY_FOUR_HOUR_PERIOD))
//      logger.info(s"Completed data pump for $entityType from $lastSyncTime. Total audit events ${auditAccessEvents.size}")
//    } catch {
//      case e: Exception => logger.error(s"unable to pump data for $entityType from $lastSyncTime", e)
//    }
//  }
//
//  def manualPump( entityType: String, start: Date, end: Date ) = {
//    val startDate = new DateTime().withMillis(start.getTime)
//    val daysCount = Days.daysBetween(startDate, new DateTime().withMillis(end.getTime)).getDays()
//    logger.debug(s"Number of days between $start and $end = $daysCount")
//    (0 to daysCount).map(startDate.plusDays(_)).foreach { day =>
//      val currentTime = System.currentTimeMillis()
//      logger.info(s"starting $entityType pump for ${day.toDate}")
//      val auditAccessEvents = eventStore.select(entityType, day.toDate)
//      auditAccessEvents.foreach { event =>
//        try{
//          logger.debug(s"Audit access event is $event")
//          tableStore.insert(event)
//        }
//        catch {
//          case e: Exception => logger.error(s"Failed to insert $event", e)
//        }
//      }
//      val finishedPumpTime = System.currentTimeMillis()
//      logger.info(s"ending $entityType pump for ${day.toDate}, took: ${finishedPumpTime - currentTime}. Total audit events ${auditAccessEvents.size}")
//    }
//  }

  override def pump(startDate: Date): Unit = {
    try {
      logger.info(s"starting to pump data for from $startDate")
      val count = prestoStore.fetch(LocalDate.ofEpochDay(startDate.getTime))
      logger.info(s"select completed data for from $startDate with count of ${count.size}")
      count.foreach { counter =>
        logger.debug(s"count is $counter")
        warehouseStore.insert(counter)
      }
      logger.info(s"Completed data pump for from $startDate with count of ${count.size}")
    } catch {
      case e: Exception => logger.error("unable to pump data", e)
    }

  }

}
