package sparks3logger

import org.apache.spark.streaming._

import java.util.{Collections, Properties}
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer

import scala.util.matching.Regex
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import org.apache.log4j._
import org.apache.spark.sql.SparkSession

object config {

  val log = Logger.getLogger("sparks3logger.config")

  log.info("initializing config")

  val spark = SparkSession.builder.getOrCreate
  //spark.sparkContext.setLogLevel("INFO")

  val batchTime = Duration(spark.conf.get("spark.LOGGER_TOPICS_BATCH_TIME_IN_SECONDS").toInt * 1000)

  val topicsPerStream = spark.conf.get("spark.LOGGER_TOPICS_PER_STREAM").toInt

  val s3WritesAttempts = 3

  val s3ConcurrentWrites = spark.conf.get("spark.LOGGER_S3_CONCURRENT_WRITES").toInt

  //val broker = "kafka.local:9092"
  val broker = spark.conf.get("spark.LOGGER_BROKER")

  val s3ParquetDirectory = spark.conf.get("spark.LOGGER_PARQUET_DIR")

  val s3Bucket = spark.conf.get("spark.LOGGER_BUCKET")

  val s3RawDirectory = spark.conf.get("spark.LOGGER_RAW_DIR")

  val localDirectory = spark.conf.get("spark.LOGGER_LOCAL_DIR")

  val localRawDirectory = spark.conf.get("spark.LOGGER_LOCAL_RAW")

  val continueS3Writes = true

  val timestampPattern = new Regex("[\"]{1}[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}Z[\"]{1}")

  val props = new Properties()
  props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, broker)
  props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
  props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")

  var groupid = "spark-logger_" + spark.conf.get("spark.LOGGER_CLUSTERID")

  var session_timeout = spark.conf.get("spark.LOGGER_SESSION_TIMEOUT")

  var heartbeat = spark.conf.get("spark.LOGGER_HEARTBEAT")

  var request_timeout = spark.conf.get("spark.LOGGER_REQUEST_TIMEOUT")

  val kafkaParams = scala.collection.mutable.Map(
    "bootstrap.servers" -> broker,
    "group.id" -> groupid,
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "enable.auto.commit" -> (true: java.lang.Boolean),
    "auto.offset.reset" -> "latest",
    "session.timeout.ms" -> session_timeout,
    "heartbeat.interval.ms" -> heartbeat,
    "request.timeout.ms" -> request_timeout
  )

  //val creds = new BasicAWSCredentials(sys.env("AWS_ACCESS_KEY_ID"), sys.env("AWS_SECRET_ACCESS_KEY"))

  val athena_driver = "com.amazonaws.athena.jdbc.AthenaDriver"

  var explicit_topics = spark.conf.get("spark.LOGGER_OVERRIDE_EXPLICIT_TOPICS")

  var s3_writer_wait = spark.conf.get("spark.LOGGER_S3_WRITER_WAIT").toInt

  var s3_endpoint = spark.conf.get("spark.LOGGER_S3_ENDPOINT").toString

  val client = new AmazonS3Client()

  if (s3_endpoint.length > 0) {
    client.setEndpoint(s3_endpoint);
    client.setS3ClientOptions(new com.amazonaws.services.s3.S3ClientOptions().withPathStyleAccess(true))
  }

}
