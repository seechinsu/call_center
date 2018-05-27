package controllers

import javax.inject._
import play.api._
import play.api.mvc._

class Health @Inject()(cc: ControllerComponents, configuration: Configuration) extends AbstractController(cc) {

  def index() = Action { Ok }

  def ping() = Action { Ok }

  def info() = Action { Ok }

  def health() = Action { Ok }
}
