package client

import client.ConsumerActor.{Commit, Fetch, GetInfo, InfoResult}
import client.SupervisorActor.GetChild

import akka.actor.SupervisorStrategy.{Escalate, Restart, Stop}
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, OneForOneStrategy, Props, Terminated}
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.consumer.{KafkaConsumer, OffsetAndMetadata, OffsetCommitCallback}
import org.apache.kafka.common.TopicPartition

import java.util.concurrent.TimeUnit
import javax.inject.Inject
import scala.collection.JavaConverters._
import scala.concurrent.Future

class KafkaConsumerClient @Inject()(config: KafkaConsumerConfiguration) extends LazyLogging {

  val consumer = new KafkaConsumer[Array[Byte], Array[Byte]](config)

  def poll: Iterable[InputEvent] = {
    consumer.poll(config.pollTimeout.toMillis).asScala map (InputEvent(_))
  }

  def commitSync: Unit = consumer.commitSync()

  def commitAsync: Unit = consumer.commitAsync(
    new OffsetCommitCallback {
      override def onComplete(offsets: java.util.Map[TopicPartition, OffsetAndMetadata], e: Exception) = {
        if (e != null) {
          logger.error(s"commit failed", e)
        } else {
          logger.debug(s"committed $offsets")
        }
      }
    }
  )

  def close: Unit = consumer.close()

  def subscribe(): Unit = consumer.subscribe(config.topics.asJava)

  def unsubscribe(): Unit = consumer.unsubscribe()

  def consumerInfo(): String = s"group ${config.groupId} consuming ${config.topics mkString ","}"
}


import com.typesafe.config.Config
import org.apache.kafka.clients.consumer.ConsumerConfig

import java.util.Properties
import javax.inject.{Inject, Provider}
import scala.collection.JavaConversions._
import scala.concurrent.duration._

case class KafkaConsumerConfiguration(
  brokers: String,
  topics: Seq[String],
  groupId: String,
  pollTimeout: FiniteDuration,
  sessionTimeout: FiniteDuration,
  offsetReset: String,
  maxPartitionFetchBytes: Long,
  maxPollRecords: Int,
  heartbeatInterval: FiniteDuration,
  maxPollInterval: FiniteDuration,
  requestTimeout: FiniteDuration,
  autoCommit: Boolean = false,
  commitInterval: FiniteDuration = 1.second,
  keyDeserializer: String = "org.apache.kafka.common.serialization.ByteArrayDeserializer",
  valueDeserializer: String = "org.apache.kafka.common.serialization.ByteArrayDeserializer",
  async: Boolean = false
) {
  require(brokers.nonEmpty, "must set brokers in .conf or KAFKA_BROKERS in environment")
  require(groupId.nonEmpty, "must set groupId in .conf or KAKFA_GROUP_ID in enviroment")
  require(topics.nonEmpty, "must set topic in .conf or KAFKA_TOPIC in environment")
}

object KafkaConsumerConfiguration {
  val Topics = "topics"
  val PollTimeout = "pollTimeout"
  val GroupId = "groupId"
  val SessionTimeout = "sessionTimeout"
  val OffsetReset = "offsetReset"
  val MaxPartitionFetchBytes = "maxPartitionFetchSize"
  val MaxPollRecords = "maxPollRecords"
  val HeartbeatInterval = "heartbeatInterval"
  val MaxPollInterval = "maxPollInterval"
  val RequestTimeout = "requestTimeout"
  val async = "async"

  implicit def toProperties(config: KafkaConsumerConfiguration): Properties = {
    // field names defined by java client
    val props: Properties = new Properties
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, config.autoCommit.toString)
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, config.commitInterval.toMillis.toString)
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, config.keyDeserializer)
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, config.valueDeserializer)
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.brokers)
    props.put(ConsumerConfig.GROUP_ID_CONFIG, config.groupId)
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, config.sessionTimeout.toMillis.toString)
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, config.offsetReset)
    props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, config.maxPartitionFetchBytes.toString)
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, config.maxPollRecords.toString)
    props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, config.heartbeatInterval.toMillis.toString)
    props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, config.maxPollInterval.toMillis.toString)
    props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, config.requestTimeout.toMillis.toString)
    props
  }

  def apply(brokers: String, config: Config): KafkaConsumerConfiguration = KafkaConsumerConfiguration(
    brokers,
    config.getStringList(Topics).toList,
    config.getString(GroupId),
    FiniteDuration.apply(config.getDuration(PollTimeout).toNanos, TimeUnit.NANOSECONDS),
    FiniteDuration.apply(config.getDuration(SessionTimeout).toNanos, TimeUnit.NANOSECONDS),
    config.getString(OffsetReset),
    config.getBytes(MaxPartitionFetchBytes),
    config.getInt(MaxPollRecords),
    FiniteDuration.apply(config.getDuration(HeartbeatInterval).toNanos, TimeUnit.NANOSECONDS),
    FiniteDuration.apply(config.getDuration(MaxPollInterval).toNanos, TimeUnit.NANOSECONDS),
    FiniteDuration.apply(config.getDuration(RequestTimeout).toNanos, TimeUnit.NANOSECONDS),
    async = config.getBoolean(async)
  )
}

class KafkaConsumerConfigProvider @Inject()(
  brokerConfig: KafkaBrokerConfiguration,
  @ConsumerIdentifier section: String,
  configuration: Config
) extends Provider[KafkaConsumerConfiguration] {
  override def get(): KafkaConsumerConfiguration = {
    KafkaConsumerConfiguration(
      brokerConfig.brokers,
      configuration.getConfig("kafkaclient.consumer" + "." + section)
    )
  }
}

import com.google.inject.BindingAnnotation

import java.lang.annotation.ElementType.{FIELD, METHOD, PARAMETER}
import java.lang.annotation.RetentionPolicy.RUNTIME
import java.lang.annotation.{Retention, Target}


@BindingAnnotation
@Target(Array(Array(FIELD, PARAMETER, METHOD)))
@Retention(RUNTIME) trait ConsumerIdentifier {}

case class ConsumerActorConfiguration(
  cooldown: FiniteDuration,
  waitBetweenPolls: Boolean = false,
  restartRetries: Int = 10,
  restartDuration: FiniteDuration = 1.minute
)

object ConsumerActorConfiguration {
  def apply(config: Config): ConsumerActorConfiguration = {
    ConsumerActorConfiguration(
      FiniteDuration.apply(config.getDuration("cooldown").toNanos, TimeUnit.NANOSECONDS),
      config.getBoolean("waitBetweenPolls"),
      config.getInt("restartRetries"),
      FiniteDuration.apply(config.getDuration("restartDuration").toNanos, TimeUnit.NANOSECONDS)
    )
  }
}

class ConsumerActorConfigProvider @Inject()(@ConsumerIdentifier section: String, configuration: Config)
  extends Provider[ConsumerActorConfiguration] {
  override def get(): ConsumerActorConfiguration = {
    ConsumerActorConfiguration(configuration.getConfig("kafkaclient.consumer" + "." + section))
  }
}

class ConsumerSupervisor @Inject()(
  actorSystem: ActorSystem,
  config: ConsumerActorConfiguration,
  @ConsumerIdentifier name: String,
  consumer: KafkaConsumerClient,
  kafkaConfig: KafkaConsumerConfiguration,
  processor: ConsumerRecordProcessor,
  producer: KafkaProducerClient
) {
  val consumerProps = ConsumerActor.props(config, consumer, processor, producer, kafkaConfig)
  val supervisorActor = actorSystem.actorOf(
    SupervisorActor.props(consumerProps, name, producer, config),
    name + "-supervisor"
  )
}

class ConsumerActor(
  config: ConsumerActorConfiguration,
  consumer: KafkaConsumerClient,
  processor: ConsumerRecordProcessor,
  producer: KafkaProducerClient,
  kafkaConfig: KafkaConsumerConfiguration
)
  extends Actor with ActorLogging {

  implicit val executionContext = context.dispatcher
  val cooldown: FiniteDuration = config.cooldown
  val waitBetweenPolls: Boolean = config.waitBetweenPolls

  override def preStart: Unit = {
    log.debug("preStart")
    consumer.subscribe()
    context.system.scheduler.scheduleOnce(Duration.Zero, self, Fetch)
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    log.warning("preRestart for {}", reason)
    consumer.unsubscribe()
  }

  override def postRestart(reason: Throwable): Unit = {
    log.debug("postRestart calling preStart")
    preStart()
  }

  override def postStop: Unit = {
    commit()
    consumer.close
    log.debug("closed")
  }

  def process(record: InputEvent): Unit = {
    log.debug("processing {}", record)
    processor.process(record)
  }

  def commit(): Unit = {
    log.debug("synchronous commit")
    consumer.commitSync
  }

  override def receive: Receive = {
    case Fetch =>
      if (!kafkaConfig.async) {
        val count = fetch
        if (count > 0 && !waitBetweenPolls) {
          self ! Fetch
        } else {
          context.system.scheduler.scheduleOnce(cooldown, self, Fetch)
        }
      } else {
        var count = 0
        log.debug("async entering fetch")
        Future {consumer.poll}.flatMap(Future.traverse(_)(processor.processAsync)).map { records =>
          val count = records.size
          log.debug("async committing offsets")
          consumer.commitAsync
          log.debug(s"async $count records processed and committed")
          if (count > 0 && !waitBetweenPolls) {
            self ! Fetch
          } else {
            context.system.scheduler.scheduleOnce(cooldown, self, Fetch)
          }
        }
      }
    case Commit => commit
    case GetInfo => sender ! InfoResult(consumer.consumerInfo)
  }

  def fetch(): Int = {
    var count = 0
    log.debug("entering fetch loop")
    for (record <- consumer.poll) {
      process(record)
      count += 1
    }

    log.debug("committing offsets")
    consumer.commitAsync
    log.debug(s"$count records processed and committed")
    count
  }
}

object ConsumerActor {

  sealed trait Control

  case object Commit extends Control

  case object Fetch extends Control

  case object GetInfo extends Control

  case class InfoResult(info: String) extends Control

  def props(
    config: ConsumerActorConfiguration,
    consumer: KafkaConsumerClient,
    processor: ConsumerRecordProcessor,
    producer: KafkaProducerClient,
    kafkaConfig: KafkaConsumerConfiguration
  ): Props = {
    Props(new ConsumerActor(config, consumer, processor, producer, kafkaConfig))
  }
}

class SupervisorActor(
  child: Props,
  childName: String,
  producer: KafkaProducerClient,
  config: ConsumerActorConfiguration
) extends Actor with ActorLogging {

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = config.restartRetries, withinTimeRange = config.restartDuration) {
      case e: Exception =>
        childRestartHook(e)
        Restart
      case t => super.supervisorStrategy.decider.applyOrElse(t, (_: Any) => Escalate)
    }

  val ErrorTopic: Option[String] = Some("consumer-errors")
  val childActor: ActorRef = context.actorOf(child, childName)
  context.watch(childActor)

  var childInfo: String = "info not available"
  childActor ! GetInfo

  def receive = {
    case InfoResult(info) =>
      childInfo = info
    case GetChild =>
      sender ! childActor
    case Stop =>
      context.unwatch(childActor)
      context.system.stop(childActor)
    case Terminated(childActor) =>
      log.error("child {} has died", childName)
      for (topic <- ErrorTopic) {
        //producer.publish(topic, serialize.serialize(ConsumerProblem(s"terminated $childName ($childInfo)")))
      }
  }

  def childRestartHook(reason: Exception): Unit = {
    log.warning("child restarted because of {}", reason)
    for (topic <- ErrorTopic) {
      //producer.publish(topic, serialize.serialize(ConsumerProblem(s"restarting $childName ($childInfo)", reason)))
    }
  }
}

object SupervisorActor {

  case object GetChild

  case object Stop

  def props(
    child: Props,
    childName: String,
    producer: KafkaProducerClient,
    config: ConsumerActorConfiguration
  ): Props = {
    Props(new SupervisorActor(child, childName, producer, config))
  }
}
