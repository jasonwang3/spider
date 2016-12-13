package com.spider.api.controllers

import javax.inject.{Inject, Named}

import akka.actor.{ActorRef, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
import play.api.Logger
import play.api.libs.json.JsValue
import play.api.mvc._

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
  * Created by jason on 16-10-10.
  */
class SpiderController @Inject()(system: ActorSystem)(@Named("spiderServiceActor") serviceActor: ActorRef)(implicit ec: ExecutionContext) extends Controller {
  val logger: Logger = Logger(this.getClass)
  //gloabl time out setting
  implicit val timeout: Timeout = 3.seconds

  def index = Action {
    Ok("It works!")
  }

  def startSpider = Action.async { request =>
    val body: Option[JsValue] = request.body.asJson
    logger.debug(s"received spider request, body is = ${body.get}")
    (serviceActor ? body.get.toString()).map {
      case message: String => Ok("success")
      case ex: Exception => InternalServerError("system occurs error")
    }
  }

}
