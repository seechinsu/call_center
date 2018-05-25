package service

import com.typesafe.scalalogging.LazyLogging
import org.joda.time.{DateTime, Days, LocalDate, Seconds}

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

object SchedulerUtils extends LazyLogging {
  lazy val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
  lazy val simpleDateFormatter = new SimpleDateFormat("yyyy-MM-dd")

  def generateDateRange(startDate: Date, endDate: Date, simpleDateFormatter: SimpleDateFormat) = {
    val numberOfDays = Days.daysBetween(LocalDate.fromDateFields(startDate), LocalDate.fromDateFields(endDate)).getDays()
    val dateRange = for (f<- 0 to (numberOfDays - 1)) yield {
      (simpleDateFormatter.format(LocalDate.fromDateFields(startDate).plusDays(f).toDate),
        simpleDateFormatter.format(LocalDate.fromDateFields(startDate).plusDays(f + 1).toDate))
    }
    dateRange
  }

  def getDate(dateHr: String): Date = try{
    sdf.parse(dateHr)
  } catch {
    case e: Exception =>
      logger.error(s"error $dateHr is unparsable", e)
      new Date()
  }
  def convertMillisToHours(getInMillis: Long) = getInMillis / (1000*60*60)

  def getStartAndEndTimes(hours: Option[Long] = None): (Date, Date) = {
    val cal = Calendar.getInstance()
    val startTime = Calendar.getInstance().getTime
    cal.setTime(startTime)
    cal.add(Calendar.HOUR, hours.map(x => (x.toInt + 1) * -1).getOrElse(-24))
    (cal.getTime, startTime)
  }

  def getFirstAndLastDayOfTheDateRange(start: Date, end: Date): (Date, Date) = {
    val calStart = Calendar.getInstance()
    val calEnd = Calendar.getInstance()

    calStart.setTime(start)
    calStart.set(Calendar.DAY_OF_MONTH, calStart.getActualMinimum(Calendar.DAY_OF_MONTH))
    calStart.set(Calendar.HOUR_OF_DAY, 0)
    calStart.set(Calendar.MINUTE, 0)
    calStart.set(Calendar.SECOND, 0)
    val startOfMonth = calStart.getTime

    calEnd.setTime(end)
    calEnd.set(Calendar.DAY_OF_MONTH, calEnd.getActualMaximum(Calendar.DAY_OF_MONTH))
    calEnd.set(Calendar.HOUR_OF_DAY, 23)
    calEnd.set(Calendar.MINUTE, 59)
    calEnd.set(Calendar.SECOND, 59)
    calEnd.set(Calendar.MILLISECOND, 999)
    val endOfMonth = calEnd.getTime

    (startOfMonth, endOfMonth)
  }

  def nextExecutionInSeconds(hour: Int, minute: Int, scheduleInterval: Int): Int = {
    Seconds
      .secondsBetween(
        new DateTime(),
        nextExecution(hour, minute, scheduleInterval)
      )
      .getSeconds
  }

  def nextExecution(hour: Int, minute: Int, scheduleInterval: Int): DateTime = {
    val next = new DateTime()
      .withHourOfDay(hour)
      .withMinuteOfHour(minute)
      .withSecondOfMinute(0)
      .withMillisOfSecond(0)

    if (next.isBeforeNow) next.plusMillis(scheduleInterval) else next
  }
}
