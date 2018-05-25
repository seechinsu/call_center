package service

import service.utils.ScheduleInterval

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging
import play.api.Configuration

import javax.inject.{Inject, Named}
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.language.postfixOps

class QueueScheduler @Inject()(
  system: ActorSystem,
  config: Configuration,
  @Named("queueScheduler") scheduleInterval: ScheduleInterval,
  queueService: QueueService
)
  (implicit ec: ExecutionContext) extends LazyLogging {
  val simpleDateFormatter = SchedulerUtils.simpleDateFormatter
  system.scheduler.schedule(0 seconds, scheduleInterval.getInMillis milliseconds) {
    logger.info(s"scheduler running starting at ${scheduleInterval.getInMillis}")
    val (doRun, service, start, end, id) = queueService.dequeue()
    logger.info(s"scheduler running with ($doRun, $service, $start, $end)")
    try {
      if (doRun) {
        val startDate = simpleDateFormatter.parse(start)
        val endDate = simpleDateFormatter.parse(end)

        service match {
          case _ =>
            logger.error(s"scheduler unable to run with ($service, $start, $end) as $service is unsupported")
        }
        logger.info(s"scheduler running with (Cleared, $service, $start, $end)")
        queueService.updateStatus(id, "Cleared")
      }
    }
    catch {
      case e: Exception =>
        logger.error(s"failed to run $service with $start, $end", e)
        queueService.updateStatus(id, "Error")
    }
    finally {
      logger.info(s"scheduler cleanup")
      queueService.cleanup()
    }
  }
}
