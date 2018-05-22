package module

import client._

import com.google.inject.PrivateModule
import com.google.inject.name.Names

abstract class ConsumerAbstractModule extends PrivateModule {
  val name: String
  def bindProcessor: Unit

  override def configure: Unit = {
    bind(classOf[ConsumerSupervisor]).annotatedWith(Names.named(name)).to(classOf[ConsumerSupervisor]).asEagerSingleton()
    expose(classOf[ConsumerSupervisor]).annotatedWith(Names.named(name))
    bindConstant().annotatedWith(classOf[ConsumerIdentifier]).to(name)
    bind(classOf[KafkaConsumerConfiguration]).toProvider(classOf[KafkaConsumerConfigProvider])
    bind(classOf[ConsumerActorConfiguration]).toProvider(classOf[ConsumerActorConfigProvider])
    bind(classOf[KafkaConsumerClient]).asEagerSingleton()
    bind(classOf[KafkaBrokerConfiguration]).toProvider(classOf[KafkaBrokerConfigurationProvider])
    bindProcessor
  }
}
