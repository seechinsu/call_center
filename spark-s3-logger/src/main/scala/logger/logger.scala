package sparks3logger

import org.apache.spark.streaming._
import org.apache.spark.SparkContext
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

import scala.collection.JavaConversions._
import java.util.{Collections, Properties}
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession

import scala.io.Source
import java.io.FileWriter
import java.io.File

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future, Awaitable, blocking}

import scala.util.matching.Regex
import org.joda.time.DateTime

import org.apache.log4j._

object logger extends App {

  val log = Logger.getLogger("sparks3logger.logger")

  val spark = config.spark

  var ssc = new StreamingContext(spark.sparkContext, config.batchTime)

  val consumer = new KafkaConsumer[String, String](config.props)

  // if explicit_topics is populated then use, else get list from kafka
  var topicsList = Set.empty[String]
  if (!config.explicit_topics.isEmpty) {
    topicsList = config.explicit_topics.split(",").toSet
  }
  else {
    topicsList = consumer.listTopics().keys.toSet.filter(!_.startsWith("_")).filter(_ != "all-events")
  }

  topicsList = topicsList.intersect(schemaByTopic.schemas.keys.toSet)

  // Create Local Directories if they do not exist
  if (!new File(config.localDirectory).exists()) {
    new File(config.localDirectory).mkdir()
  }

  if (!new File(config.localRawDirectory).exists()) {
    new File(config.localRawDirectory).mkdir()
  }

  def setupConsumers(ssc: org.apache.spark.streaming.StreamingContext, topicsList: List[String]) {

    var topicsListParse = topicsList

    var streamTopics = List[List[String]]()
    while (topicsListParse.nonEmpty) {
      streamTopics = streamTopics :+ topicsListParse.take(config.topicsPerStream)
      topicsListParse = topicsListParse.drop(config.topicsPerStream)
    }

    streamTopics.foreach { topics =>
      topics.foreach(topic => log.info(topic + " "))
    }

    log.info("Number of Streams : " + streamTopics.size)
    var i = 0
    var dstreams = streamTopics.map { topics =>
      i += 1
      config.kafkaParams("group.id") = config.groupid + "_" + i.toString

      log.info(config.kafkaParams("group.id").toString)

      KafkaUtils.createDirectStream[String, String](
        ssc,
        LocationStrategies.PreferConsistent,
        ConsumerStrategies.Subscribe[String, String](topics, config.kafkaParams)
      )
    }
    log.info("Number of Streams Created : " + dstreams.size)

    var combinedStreams = ssc.union(dstreams)

    combinedStreams.foreachRDD { (rdd, time) =>

      log.info(">>> time : (" + time + ") rdd.partitions.size: (" + rdd.partitions.size + ") dtime: " + (System.currentTimeMillis - time.milliseconds) + "ms")
      log.info(topicsList.toString)

      var rddsByTopic = topicsList.map { topic =>
        rdd.filter { record =>
          record.topic == topic
        }
      }

      var i = 0
      rddsByTopic.foreach { rddByTopic =>

        var topicName = topicsList.get(i)

        var json = rddByTopic.map { record =>
          record.value
        }.collect().mkString("\n")

        //If there is any json data, then write
        if (json.replace("\n", "").length() > 0) {
          var jsonFile = new FileWriter(config.localDirectory + time.milliseconds + "_" + topicName + ".temp", true)
          jsonFile.write(json)
          jsonFile.close()
          new File(config.localDirectory + time.milliseconds + "_" + topicName + ".temp").renameTo(new File(config.localDirectory + time.milliseconds + "_" + topicName + ".json"))
        }
        i += 1
      }
    }
  }

  def startS3Writes(spark: org.apache.spark.sql.SparkSession): scala.concurrent.Future[Unit] = {
    blocking {
      Future {
        while (config.continueS3Writes) {
          rawToParquet.writeToS3(spark)
          Thread.sleep(config.s3_writer_wait)
        }
      }
    }
  }

  val s3WritesFuture = startS3Writes(spark)

  setupConsumers(ssc, topicsList.toList)

  ssc.start

  ssc.awaitTermination()

}
