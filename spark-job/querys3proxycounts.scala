import java.net.InetAddress
import com.amazonaws.services.s3.{AmazonS3Client, S3ClientOptions}
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.model.ListObjectsRequest
import com.amazonaws.services.s3.model.{CannedAccessControlList, PutObjectRequest}
import com.amazonaws.services.s3.model.CreateBucketRequest
import scala.collection.JavaConverters._
import org.apache.log4j.Logger

val bucket = "company"
val successSuffix = "_SUCCESS"

val client = new AmazonS3Client()
client.setEndpoint("http://s3.amazonaws.com:8080")
client.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess(true))

// ipv4 to force aws s3 client to use path style access
val s3EndPoint = InetAddress.getByName("s3.amazonaws.com").toString.split('/')(1)
sc.hadoopConfiguration.set("fs.s3a.endpoint", s"http://$s3EndPoint:8080")

val log = Logger.getRootLogger

val req = new ListObjectsRequest().withBucketName(bucket).withPrefix("data/event/parquet/")
val listing = client.listObjects(req).getObjectSummaries.asScala.map(_.getKey)

listing.filter(_.endsWith(successSuffix)).foreach { file =>
  val parquetFilePath = file.dropRight(successSuffix.length)
  log.warn(parquetFilePath)

  val parquetDataframe = spark.read.format("parquet").load(s"s3a://$bucket/$parquetFilePath")

  val parsedPathArray = parquetFilePath.split("/").reverse
  log.warn(s"Received in Parquet ${parsedPathArray(1)} ${parsedPathArray(0)} ${parquetDataframe.count}")
}

System.exit(0)
