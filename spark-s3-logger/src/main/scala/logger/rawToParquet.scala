package sparks3logger

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import scala.concurrent.duration._
import scala.io.Source
import java.io.FileWriter
import java.io.File
import java.io.PrintWriter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future, Awaitable, blocking}
import scala.language.postfixOps
import scala.collection.mutable.ListBuffer

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.commons.io.IOUtils;

import play.api.libs.json._
import java.io.FileWriter

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.{CannedAccessControlList, PutObjectRequest}
import com.amazonaws.services.s3.model.CreateBucketRequest
import java.io.IOException
import scala.util.control.NonFatal

//import com.typesafe.scalalogging.StrictLogging
//import com.typesafe.scalalogging._

import org.apache.log4j._

//object rawToParquet extends StrictLogging {
object rawToParquet {

  val log = Logger.getLogger("sparks3logger.rawToParquet")

  def flatten(js: JsValue, prefix: String = ""): JsObject = js.as[JsObject].fields.foldLeft(Json.obj()) {
    case (acc, (k, v: JsObject)) => {
      if (prefix.isEmpty) acc.deepMerge(flatten(v, k))
      else acc.deepMerge(flatten(v, prefix + "_" + k))
    }
    case (acc, (k, v)) => {
      if (prefix.isEmpty) acc + (k -> v)
      else acc + (prefix + "_" + k -> v)
    }
  }

  def removeNullFields(js: JsObject): JsObject = {
    var ne = Seq.empty[(String, play.api.libs.json.JsValue)]
    js.as[JsObject].fields.foreach { ele =>
      if (ele._2.toString != "\"\\\\N\"")
        ne = ne :+ (ele._1, ele._2)
    }
    JsObject(ne)
  }

  def writeTopic(client: com.amazonaws.services.s3.AmazonS3Client, spark: org.apache.spark.sql.SparkSession, topicName: String, day: String, times: List[String]) {

    val conf = new Configuration()
    val fs = FileSystem.get(conf)

    val combinedRawFileName = config.localRawDirectory + times(0).toString + "_" + topicName
    log.info("combinedRawFileName : " + combinedRawFileName)
    val combinedFileName = "/" + day + topicName
    log.info("combinedFileName : " + combinedFileName)

    try {

      val combinedRawFiles = new FileWriter(combinedRawFileName)
      val combinedFiles = fs.create(new Path(combinedFileName))
      val writer = new PrintWriter(combinedFiles)

      times.foreach { time =>

        //Flatten Json for parquet
        writer.write(
          Source.fromFile(config.localDirectory + time + "_" + topicName).getLines().map { line =>

            var js = Json.parse(line)
            removeNullFields(flatten(js)).toString

          }.mkString("\n") + "\n"
        )

        //Raw
        combinedRawFiles.write(Source.fromFile(config.localDirectory + time + "_" + topicName).getLines.mkString("\n") + "\n")

      }
      writer.close()
      combinedRawFiles.close()

      try {
        writeFileToS3(
          client,
          config.s3Bucket,
          config.s3RawDirectory + topicName.replace(".json", "") + "/" + day + "/",
          combinedRawFileName,
          config.s3WritesAttempts
        )

        //read json with defined schema
        var allFrames = spark.read.schema(schemaByTopic.schemas(topicName.replace(".json", ""))).json(combinedFileName)

        if (allFrames.count > 0) {

          try {
            if (config.s3_endpoint.length > 0)
              writeDataframeToParquetS3Dev(
                client,
                allFrames,
                config.s3Bucket,
                config.s3ParquetDirectory,
                topicName.replace(".json", ""),
                day,
                config.s3WritesAttempts
              )
            else
              writeDataframeToParquetS3(
                client,
                allFrames,
                config.s3Bucket,
                config.s3ParquetDirectory,
                topicName.replace(".json", ""),
                day,
                config.s3WritesAttempts
              )

            // clean Completed Files
            times.foreach { time =>
              log.info("deleting " + config.localDirectory + time + "_" + topicName)
              new File(config.localDirectory + time + "_" + topicName).delete()
            }

            //Delete the combined file
            fs.delete(new Path(combinedFileName), true)
          }
          catch {
            case NonFatal(e) => log.warn("Failed all attempts to write to S3 : " + topicName + " : Exception caught: " + e)
          }
        }
      }
      catch {
        case NonFatal(e) => log.warn("writeTopic : " + topicName + " : Exception caught: " + e);
        }
      }
    catch {
      case NonFatal(e) => log.warn("error combining file, check hdfs : " + topicName + " : Exception caught: " + e);
    }

  }

  def getListOfFiles(dir: File, extensions: List[String]): List[File] = {
    dir.listFiles.filter(_.isFile).toList.filter { file =>
      extensions.exists(file.getName.endsWith(_))
    }
  }

  def writeDataframeToParquetS3Dev(client: com.amazonaws.services.s3.AmazonS3Client, dataFrame: org.apache.spark.sql.DataFrame, bucket: String, path:String, topicName: String, dayPartition: String, s3attempts: Int) = {

    log.warn(bucket.toString)

    val tempParquetDirectory = "/stage/" + topicName + "/" + dayPartition + "/"

    var beforeList = List[java.io.File]()

    if (new java.io.File(tempParquetDirectory).exists) {
      beforeList = getListOfFiles(new File(tempParquetDirectory), List("parquet", "_SUCCESS"))
    }

    dataFrame.write.mode("append").parquet(tempParquetDirectory)
    log.warn("parquet : s3n://" + bucket + "/" + path + topicName + "/" + dayPartition + " count : " + dataFrame.count)

    val afterList = getListOfFiles(new File(tempParquetDirectory),List("parquet", "_SUCCESS"))

    val files = afterList diff beforeList

    files.foreach{ filename =>
      client.putObject(new PutObjectRequest(bucket, path + filename.toString.replace("/stage","").drop(1), new File(filename.toString)));
    }
  }

  def writeDataframeToParquetS3(client: com.amazonaws.services.s3.AmazonS3Client, dataFrame: org.apache.spark.sql.DataFrame, bucket: String, path:String, topicName: String, dayPartition: String, s3attempts: Int) = {

    var attempts = s3attempts

    while (attempts > 0) {
      try {
        dataFrame.select(dataFrame.columns.map(c => col(c.toLowerCase)): _*)
          .write.mode("append")
          .parquet(
            "s3n://" + bucket + "/" + path + topicName + "/" + dayPartition
          )
        log.warn(
          "parquet : s3n://" + bucket + "/" + path + topicName + "/" + dayPartition + " count : " + dataFrame.count
        )

        attempts = 0
      }
      catch {
        case NonFatal(e) => log.warn("writeTopic : " + topicName + " : Exception caught: " + e)
          if (attempts == 0)
            throw e
      }

      attempts -= 1
    }

  }

  def writeFileToS3(client: com.amazonaws.services.s3.AmazonS3Client, s3Directory: String, s3Path: String, localFilename: String, s3WritesAttempts: Int) = {

    val uploadFile = new File(localFilename)

    var attempts = s3WritesAttempts
    while (attempts > 0) {

      try {
        client.putObject(config.s3Bucket, s3Path + uploadFile.getName(), uploadFile)

      } catch {
        case NonFatal(e) => log.warn("writeFileToS3 : Exception caught : writing " + config.s3Bucket + "/" + s3Path + uploadFile.getName() + " : " + e)
        if ( attempts == 0 )
          throw e
      }

      attempts -= 1
    }
  }

  def writeToS3(spark: org.apache.spark.sql.SparkSession) = {

    val files = new File(config.localDirectory).listFiles.filter(_.isFile).filter(_.getName.endsWith(".json")).map(_.getName)

    val fileMap = files.map { filename =>
      (filename.substring(0, 13), filename.substring(14, filename.length))
    }

    log.info("files : " + files.toString)

    import spark.implicits._
    spark.sparkContext.parallelize(fileMap).toDF().createOrReplaceTempView("filesToProcess")

    // Query to get file lists by topic and day
    val query = s"select _2 as topic, cast(from_unixtime(_1/1000) as date) as day, collect_list(_1) as times, count(1), min(_1) as cnt from filesToProcess group by 1, 2 order by count(1) desc, 3 asc"

    //create a Future by topic and day
    val futures = spark.sql(query).limit(1).collect().map { row =>
      blocking {
        Future {
          val currentTopicName = row(0).toString
          val day = row(1).toString
          val times = row(2).asInstanceOf[scala.collection.mutable.WrappedArray[String]].toList // list of file times by day
          log.warn("writeTopic : " + currentTopicName + " for day " + day + " - files : " + times.size)

          writeTopic(config.client, spark, currentTopicName, day, times)
        }
      }
    }

    Await.ready(Future.sequence(futures.to[ListBuffer]), 1200 seconds)

    log.info("s3 writer sleeping ...")
  }

}
