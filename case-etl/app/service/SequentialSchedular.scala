package service

import service.utils.ScheduleInterval

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging
import play.api.Configuration

import java.text.SimpleDateFormat
import javax.inject.{Inject, Named}
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.language.postfixOps

class SequentialSchedular @Inject()(
  system: ActorSystem,
  config: Configuration,
  @Named("prestoPumpScheduleInterval") scheduleInterval: ScheduleInterval,
  queueService: QueueService
)
  (implicit ec: ExecutionContext) extends LazyLogging {

  val simpleDateFormatter = SchedulerUtils.simpleDateFormatter
  val services = List("all")
  val nextValidHourAfter = config.get[Int]("nextExecutionHour")
  val nextValidMinutesAfter = config.get[Int]("nextExecutionMinutes")
  val nextValidTimeAfter = SchedulerUtils.nextExecutionInSeconds(
    nextValidHourAfter,
    nextValidMinutesAfter,
    scheduleInterval.getInMillis().toInt
  )
  logger.info(s"ready to schedule sequentially in ${nextValidTimeAfter seconds} with ${scheduleInterval.getInMillis milliseconds}")
  logger.info(s"ready to schedule in ${nextValidTimeAfter seconds}")
  system.scheduler
    .schedule(nextValidTimeAfter seconds, scheduleInterval.getInMillis milliseconds) {
      logger.info(s"starting at ${scheduleInterval.getInMillis}")
      val endHours = SchedulerUtils.convertMillisToHours(scheduleInterval.getInMillis) hours
      val (startDate, endDate) = SchedulerUtils.getStartAndEndTimes(Some(endHours.toHours))
      logger.info(
        s"""
           |pumping for all at $startDate to $endDate with ${endHours.toHours} and
           |${simpleDateFormatter.format(startDate)} and ${simpleDateFormatter.format(endDate)}
         """.stripMargin)

      services.foreach(x =>
        queueService.enqueue(
          x,
          simpleDateFormatter.format(startDate),
          simpleDateFormatter.format(endDate)
        )
      )
    }

}
