package controllers

import report.{AppConfig, CompositeReport, CompositeService, ReportService, Warehouse, YamlLoader}

import javax.inject._
import play.api._
import play.api.mvc._

import java.util.Date
import utils.Utils._

import org.joda.time.{DateTime, Weeks}

import java.io.File

class ApiDocsController @Inject()(
  cc: ControllerComponents,
  configuration: Configuration
) extends AbstractController(cc) {

  def redirectToDocs = Action {
    val basePath = configuration.underlying.getString("swagger.api.uri")
    Redirect(
      url = "/assets/lib/swagger-ui/index.html",
      queryString = Map("url" -> Seq(s"$basePath/swagger.json"))
    )
  }

}


class Health @Inject()(
  cc: ControllerComponents,
  configuration: Configuration,
  warehouse: Warehouse
) extends AbstractController(cc) {

  def health = Action {
    Ok
  }

  def health_warehouse(catalog: String) = Action { implicit request =>
    val booleans = warehouse.catalogHealthy(catalog)
    Ok(booleans)
  }

}

class Job @Inject()(
  cc: ControllerComponents,
  configuration: Configuration,
  warehouse: Warehouse,
  reports: ReportService,
  compositeService: CompositeService
) extends AbstractController(cc) {

  def get_time_reference = Action {
    Ok(reports.get_time_reference())
  }

  def set_time_reference(time: Date) = Action { implicit request =>
    Ok(reports.set_time_reference(time))
  }

  def jobs_auto = Action { implicit request =>
    reports.queue_jobs()
    reports.disable_failing_reports()
    reports.reset_stuck_jobs()
    val job = reports.do_job()
    job.map(x => Ok(x)).getOrElse(Ok("no outstanding jobs"))
  }

  def run_all_reports = Action { implicit request =>
    val results = reports.run_jobs()
    Ok(results)
  }

  def run_next_job = Action { implicit request =>
    Ok(reports.do_job())
  }

  def run_earliest_reports(num: Int) = Action { implicit request =>
    reports.run_jobs(Some(num))
    Ok("ran earliest %s reports".format(num))
  }

  def enqueue_needed_jobs = Action { implicit request =>
    Ok(reports.queue_jobs())
  }

  def jobs_summary = Action { implicit request =>
    Ok(reports.jobs_summary())
  }
}

class Composite @Inject()(
  cc: ControllerComponents,
  configuration: Configuration,
  config: AppConfig,
  composite: CompositeService,
  yaml: YamlLoader) extends AbstractController(cc) {
  def composite_view(report_name: String) = Action { implicit request =>
    val start = date_from_query("start", DateTime.now().minusMonths(1).toDate)
    val end = date_from_query("end")
    val offset = uint_from_query("offset", 0).toString
    val limit = uint_from_query("limit", 1000).toString
    val filters = list_from_query("filters").mkString(" ")

    check_untrusted(filters)

    val ds = composite.composite_view(report_name, start, end, filters, offset, limit)
    Ok(ds)
  }
    

  def get_composites() = Action { implicit request =>
    Ok(composite.get_all_composites())
  }


  def get_composite(name: String) = Action { implicit request =>
    Ok(composite.get_composite(name))
  }


  def post_composite() = Action { implicit request =>
    val yml = request.body.asJson.map(_.as[CompositeReport])
    Ok(composite.create_composite_from_yaml(yml.get))
  }

  def delete_composite(name: String) = Action { implicit request =>
    Ok(composite.delete_composite(name))
  }

  def load_composites()= Action { implicit request =>
    composite.autoload_composites(config.PATH_COMPOSITES)
    Ok()
  }
}

class Report @Inject()(
  cc: ControllerComponents,
  configuration: Configuration,
  composite: CompositeService,
  reports: ReportService,
  config: AppConfig,
  yaml: YamlLoader) extends AbstractController(cc) {
  
  def get_report(report_id_or_name: String)= Action { implicit request =>
  Ok(reports.get_report(report_id_or_name))}


  def get_report_details(report_id_or_name: String)= Action { implicit request =>
  Ok(reports.get_report_details(report_id_or_name))}


  def delete_report(report_id_or_name: String)= Action { implicit request =>
  Ok(reports.delete_report(report_id_or_name))}


  def get_reports()= Action { implicit request =>
  val (cursor, count) = paging_from_query()
  Ok(reports.get_reports(cursor.toString, count))}


  def post_report()= Action { implicit request =>
  val yml = request.body.asJson.map(_.as[report.Report])
  yml.foreach(reports.create_report_from_yaml)
  Ok()
  }


  def load_reports()= Action { implicit request =>
    reports.autoload_reports(config.PATH_REPORTS)
  Ok()
  }
  
  def load_report(name: String)= Action { implicit request =>
  Ok(reports.autoload_reports(config.PATH_REPORTS, Some(name)))
  }


  def report_view(report_id_or_name: String)= Action { implicit request =>
  val frame = request.queryString.get("frame").flatMap(_.headOption).getOrElse("day")
    val start = date_from_query("start", DateTime.now().minusMonths(1).toDate)
    val end = date_from_query("end")
  val dimensions = list_from_query("dimensions").map(x => x.split("=").head -> x.split("=").tail.head)
  val metrics = list_from_query("metrics").map(x => x.split("=").head -> x.split("=").tail.head)

  check_untrusted(dimensions.++:(metrics).mkString(" "))

  val ds = reports.report_view(report_id_or_name, frame, start, end, dimensions, metrics)
  Ok(ds)
  }


  def disable_report(report_id: String)= Action { implicit request =>
  Ok(reports.disable_report(report_id))}


  def enable_report(report_id: String)= Action { implicit request =>
  Ok(reports.enable_report(report_id))}
  }
