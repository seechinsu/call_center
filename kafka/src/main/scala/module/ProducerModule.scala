import client.{KafkaBrokerConfiguration, KafkaBrokerConfigurationProvider, KafkaProducerClient, KafkaProducerConfigProvider, KafkaProducerConfiguration}

import com.google.inject.PrivateModule

/**
  * Runtime DI which binds a singleton producer and associated actors and configurations.
  */
class ProducerModule extends PrivateModule {

  override def configure(): Unit = {
    bind(classOf[KafkaProducerConfiguration]).toProvider(classOf[KafkaProducerConfigProvider])
    bind(classOf[KafkaProducerClient]).asEagerSingleton()
    expose(classOf[KafkaProducerClient])
    bind(classOf[KafkaBrokerConfiguration]).toProvider(classOf[KafkaBrokerConfigurationProvider])
  }

}
