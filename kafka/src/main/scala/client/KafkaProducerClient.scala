package client

import serialize.serialize

import akka.util.ByteString
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}

import java.util.concurrent.TimeUnit
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future, blocking}

class KafkaProducerClient @Inject()(config: KafkaProducerConfiguration)
  (implicit ec: ExecutionContext) extends LazyLogging {
  val producer = new KafkaProducer[Array[Byte], Array[Byte]](config)

  def publish(record: EventWrapper): Future[EventReceipt] = {
    val future = producer.send(record, new Callback {
      override def onCompletion(metadata: RecordMetadata, e: Exception) = {
        if (e != null) {
          logger.error(s"publish failed", e)
        } else {
          logger.debug(s"published to $metadata")
        }
      }
    })
    Future {
      blocking {
        EventReceipt(future.get)
      }
    }
  }

  def close(): Unit = {
    producer.close(config.disconnect.toMillis, TimeUnit.MILLISECONDS)
  }
}

object KafkaProducerClient {
  def apply(config: KafkaProducerConfiguration)(implicit ec: ExecutionContext): KafkaProducerClient = {
    new KafkaProducerClient(config)(ec)
  }
}

import com.typesafe.config.Config
import org.apache.kafka.clients.producer.ProducerConfig

import java.util.Properties
import javax.inject.{Inject, Provider}
import scala.concurrent.duration.FiniteDuration

case class KafkaProducerConfiguration(
  brokers: String,
  acks: String,
  retries: Int,
  batchSize: Int,
  linger: FiniteDuration,
  bufferMemory: Long,
  disconnect: FiniteDuration,
  keySerializer: String = "org.apache.kafka.common.serialization.ByteArraySerializer",
  valueSerializer: String = "org.apache.kafka.common.serialization.ByteArraySerializer"
) {
  require(brokers.nonEmpty, "must set kafkaclient.brokers in .conf or KAFKA_BROKERS in environment")
}

object KafkaProducerConfiguration {
  // field names in application.conf
  val Acks = "acks"
  val Retries = "retries"
  val BatchSize = "batchSize"
  val Linger = "linger"
  val BufferMemory = "bufferMemorySize"
  val Disconnect = "disconnect"

  // typesafe config does not provide Option like play configuration :(
  implicit class RichConfig(val underlying: Config) extends AnyVal {
    def getOptionalString(path: String): Option[String] = if (underlying.hasPath(path)) {
      Some(underlying.getString(path))
    } else {
      None
    }
  }

  implicit def toProperties(config: KafkaProducerConfiguration): Properties = {
    // field names defined by java client
    val props: Properties = new Properties
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.brokers)
    props.put(ProducerConfig.ACKS_CONFIG, config.acks.toString)
    props.put(ProducerConfig.RETRIES_CONFIG, config.retries.toString)
    props.put(ProducerConfig.BATCH_SIZE_CONFIG, config.batchSize.toString)
    props.put(ProducerConfig.LINGER_MS_CONFIG, config.linger.toMillis.toString)
    props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, config.bufferMemory.toString)
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, config.keySerializer)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, config.valueSerializer)
    props
  }

  def apply(brokers: String, config: Config): KafkaProducerConfiguration = {
    new KafkaProducerConfiguration(
      brokers,
      config.getString(Acks),
      config.getInt(Retries),
      config.getInt(BatchSize),
      FiniteDuration.apply(config.getDuration(Linger).toNanos, TimeUnit.NANOSECONDS) ,
      config.getBytes(BufferMemory),
      FiniteDuration.apply(config.getDuration(Disconnect).toNanos, TimeUnit.NANOSECONDS)
    )
  }
}

class KafkaProducerConfigProvider @Inject()(
  brokerConfig: KafkaBrokerConfiguration,
  configuration: Config
) extends Provider[KafkaProducerConfiguration] {
  override def get(): KafkaProducerConfiguration = {
    KafkaProducerConfiguration(brokerConfig.brokers, configuration.getConfig("kafkaclient.producer"))
  }
}

case class KafkaBrokerConfiguration(brokers: String)

object KafkaBrokerConfiguration {
  val Brokers = "brokers"

  def apply(config: Config): KafkaBrokerConfiguration = {
    KafkaBrokerConfiguration(
      config.getString(Brokers)
    )
  }
}

class KafkaBrokerConfigurationProvider @Inject()(configuration: Config)
  extends Provider[KafkaBrokerConfiguration] {
  override def get(): KafkaBrokerConfiguration = {
    KafkaBrokerConfiguration(configuration.getConfig("kafkaclient"))
  }
}

case class EventWrapper(topic: String, key: ByteString, value: ByteString) {
  def withTopic(newTopic: String): EventWrapper = {
    new EventWrapper(newTopic, key, value)
  }
}
object EventWrapper {
  
  def apply(topic: String, serialized: String): EventWrapper = {
    new EventWrapper(topic, ByteString.empty, ByteString(serialized))
  }
  
  def apply(topic: String, partitionKey: String, serialized: String): EventWrapper = {
    new EventWrapper(topic, ByteString(partitionKey), ByteString(serialized))
  }
  
  def apply(event: Event): EventWrapper = {
    apply(hyphenify(event.eventType), serialize(event))
  }
  implicit def toProducerRecord(record: EventWrapper): ProducerRecord[Array[Byte], Array[Byte]] = {
    val key = if (record.key.isEmpty) null else record.key.toArray // scalastyle:off null
    val value = if (record.value.isEmpty) null else record.value.toArray // scalastyle:off null
    new ProducerRecord[Array[Byte], Array[Byte]](record.topic, key, value)
  }


  private val r1 = "([A-Z]+)([A-Z][a-z])".r
  private val r2 = "([a-z\\d])([A-Z])".r
  def hyphenify(name: String): String =
    (r2 replaceAllIn((r1 replaceAllIn(name, "$1-$2")), "$1-$2")).toLowerCase
}

trait Event extends Product {
  val eventType: String
}

case class InputEvent(topic: String, partition: Int, offset: Long, key: ByteString, value: ByteString) {
  def keyString: String = if (key.isEmpty) "" else key.utf8String
  def valueString: String = if (value.isEmpty) "" else value.utf8String
  override def toString: String = {
    s"topic: $topic P$partition O$offset, key: ${keyString}, value: ${valueString}"
  }
}

object InputEvent {
  def apply(rec: ConsumerRecord[Array[Byte], Array[Byte]]): InputEvent = {
    val key = if (rec.key == null) ByteString.empty else ByteString(rec.key) // scalastyle:off null
    val value = if (rec.value == null) ByteString.empty else ByteString(rec.value) // scalastyle:off null
    InputEvent(rec.topic, rec.partition, rec.offset, key, value)
  }
}
case class EventReceipt(topic: String, partition: Int, offset: Long)

object EventReceipt {
  def apply(record: RecordMetadata): EventReceipt = {
    EventReceipt(record.topic, record.partition(), record.offset())
  }
}
