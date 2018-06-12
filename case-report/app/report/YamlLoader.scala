package report

import io.circe

import play.api.Configuration

import javax.inject.Inject
import java.io.File
import java.nio.file.{Files, Path}
import scala.io.Source
import io.circe.{Json, ParsingFailure, parser}

import play.api.libs.json.{Reads, Writes}
import io.circe.generic.auto._
import io.circe.syntax._

import play.api._
import play.api.mvc._


class YamlLoader @Inject()(configuration: Configuration) {
  def load_yaml_from_path[T](path: String)(implicit reads: Reads[T]) = doParse(path).map(x => reads.reads(play.api.libs.json.Json.parse(x.toString())) )


  def doParse(path: String): List[Json] = {
    val file = new File(path).toPath

    val exists = Files.exists(file) // Check if the file exists

    val isDirectory = Files.isDirectory(file) // Check if it's a directory

    val isFile = Files.isRegularFile(file) // Check if it's a regular file

    val directory = if(isDirectory){
      new File(path).listFiles.filter(_.isFile).toList
    } else if(isFile) {
      List(new File(path))
    } else {
      List.empty[File]
    }
   directory.map{ x =>
     val yamlString = Source.fromFile(x).getLines().mkString("\n")
      val json: Either[ParsingFailure, Json] = parser.parse(yamlString)
      json
    }.filter(_.isRight).map(_.right.get)
  }
}
