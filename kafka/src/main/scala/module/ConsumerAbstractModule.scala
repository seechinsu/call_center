package module

import client._

import com.google.inject.PrivateModule
import com.google.inject.name.Names

abstract class ConsumerAbstractModule extends PrivateModule {
  val name: String
  def bindProcessor: Unit

  override def configure: Unit = {
    bind(classOf[ConsumerContainer]).annotatedWith(Names.named(name)).to(classOf[ConsumerContainer]).asEagerSingleton()
    expose(classOf[ConsumerContainer]).annotatedWith(Names.named(name))
    bindConstant().annotatedWith(classOf[NamedConsumer]).to(name)
    bind(classOf[KafkaConsumerConfiguration]).toProvider(classOf[KafkaConsumerConfigProvider])
    bind(classOf[ChildConsumerConfiguration]).toProvider(classOf[ChildConsumerConfigProvider])
    bind(classOf[KafkaConsumerClient]).asEagerSingleton()
    bind(classOf[KafkaProducerConfiguration]).toProvider(classOf[KafkaProducerConfigProvider])
    bindProcessor
  }
}
