package service.utils

import play.api.Configuration

import javax.inject.Inject

class RDSPumpScheduleInterval  @Inject()(
  config: Configuration
) extends ScheduleInterval {

  override def getInMillis(): Long = config.getMillis("saScheduleInterval")

}
