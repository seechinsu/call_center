package controllers

import reactivemongo.bson.BSONObjectID
import repositories.mongo.CaseRepository
import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}
import java.net.InetAddress
import call.center.worker.BuildInfo

import javax.inject.Inject

import scala.concurrent.ExecutionContext

class ApplicationInfo @Inject()(
  system: ActorSystem,
  caseRepo: CaseRepository
)(implicit ec: ExecutionContext) extends Controller with LazyLogging {

  def index: Action[AnyContent] = Action {
    Ok
  }

  def ping: Action[AnyContent] = Action(Ok("OK"))


  def info: Action[AnyContent] = Action {
    logger.debug("Application Build Info -Project Name = " + BuildInfo.name)
    Ok(
      Json.toJson(
        Map(
          "name" -> BuildInfo.name,
          "version" -> BuildInfo.version,
          "commitHash" -> BuildInfo.commitHash,
          "buildTime" -> BuildInfo.buildTime,
          "hostname" -> InetAddress.getLocalHost.getHostName
        )
      )
    )
  }

  def health: Action[AnyContent] = Action.async {
    val anyId = BSONObjectID.generate()
    val future = caseRepo.getCase(anyId)
    val result = future.map(_ => Ok("OK")).recover{case exception: Exception => ServiceUnavailable(exception.toString)}
    result
  }

  def pumpManually(service: String, start: String, end: String, queue: Option[Boolean] = None) = Action {
    Ok
  }

}
