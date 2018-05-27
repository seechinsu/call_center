package service.utils

import play.api.Configuration

import javax.inject.Inject

class PrestoPumpScheduleInterval @Inject()(
  config: Configuration
) extends ScheduleInterval {

  override def getInMillis(): Long = config.getMillis("scheduleInterval")
}
