package modules

import service.utils.{QueueScheduleInterval, ScheduleInterval}
import service.{QueueScheduler, QueueService, QueueServiceImpl, SequentialSchedular}

import com.google.inject.AbstractModule
import com.google.inject.name.Names


class QueueSequentialModule extends AbstractModule {

  @Override
  override def configure(): Unit = {
    bind(classOf[SequentialSchedular]).asEagerSingleton()
    bind(classOf[QueueScheduler]).asEagerSingleton()
    bind(classOf[QueueService]).to(classOf[QueueServiceImpl])
    bind(classOf[ScheduleInterval]).annotatedWith(Names.named("queueScheduler")).to(classOf[QueueScheduleInterval])
  }
}
