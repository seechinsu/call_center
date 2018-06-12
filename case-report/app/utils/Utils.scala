package utils

import com.typesafe.scalalogging.LazyLogging
import play.api.libs.json._
import play.api.mvc.{request, _}

import java.text.SimpleDateFormat
import java.util.Date

//import decimal
//import gevent
//import json
//import logging
//import os
//import string
//import traceback
//import yaml
//
//from datetime import datetime, date
//from functools import wraps
//
//from flask import request, Response
//
//from . import config
//from . import textgrid

//logger = logging.getLogger(__name__)

object Utils extends LazyLogging {
  def wrap_response(resp: JsValue)(implicit request: Request[AnyContent]) = {

//    resp match {
//      case JsNull => wrap_response(Json.obj("message" -> "no data available"))
//      //case JsUndefined => wrap_response(Json.obj("message" -> "no data available"))
//      case JsString(x) => wrap_response(Json.obj("message" -> x))
//      case JsArray(x) => wrap_response(Json.obj("data" -> x))
//      case JsNumber(x) => wrap_response(Json.obj("data" -> Json.obj("count" -> x)))
//    }
  }

  val inputFormat = new SimpleDateFormat("yyyy-MM-dd")

  def date_from_query(name: String, default: Date = new Date())(implicit request: Request[AnyContent]): String = {
   val dated = request.queryString.get(name).map(x => x.headOption.map(y => inputFormat.parse(y)))
    inputFormat.format(dated.flatten.getOrElse(default))
  }


  def list_from_query(name: String)(implicit request: Request[AnyContent]):Seq[String] = request.queryString.get(name).getOrElse(Seq.empty)


  def int_from_query(name: String, default: Int =0)(implicit request: Request[AnyContent]): Int = request.queryString.get(name).map(_.headOption.map(_.toInt).getOrElse(default)).getOrElse(default)


  def uint_from_query(name: String, default: Int =0)(implicit request: Request[AnyContent]):Double = Math.abs(int_from_query(name, default))


  def str_from_query(name: String, default: String ="")(implicit request: Request[AnyContent]): String =request.queryString.get(name).map(_.headOption.map(_.toString).getOrElse(default)).getOrElse(default)


  def paging_from_query() = (int_from_query("cursor", 0), int_from_query("count", 10))


  @annotation.tailrec
  def retry[T](fn: => Boolean, expect: Boolean, times: Int = 10, delay: Long = 2000): Unit = {
    val r = try { Some(fn == expect) } catch { case e: Exception if times > 1 => None }
    r match {
      case Some(true) => ()
      case _ =>
        Thread.sleep(delay)
        retry(fn, expect, times - 1, delay)
    }
  }
  val _allowed = "0123456789" + "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + ",-=_ "
  def check_untrusted(args: String) = {
    val trusted = args.toCharArray.filter(x => _allowed.exists(y => x == y))
    if(!trusted.isEmpty) throw new RuntimeException(trusted.mkString(""))
  }

}

