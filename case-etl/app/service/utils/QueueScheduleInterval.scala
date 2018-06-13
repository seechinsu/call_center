package service.utils

import play.api.Configuration

import javax.inject.Inject

class QueueScheduleInterval @Inject()(
  config: Configuration
) extends ScheduleInterval {

  override def getInMillis(): Long = config.getMillis("queueInterval")
}
