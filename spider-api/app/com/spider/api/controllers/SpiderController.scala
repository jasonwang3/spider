package com.spider.api.controllers

import akka.util.ByteString
import play.api.Logger
import play.api.http.HttpEntity
import play.api.libs.json.JsValue
import play.api.mvc._

/**
  * Created by jason on 16-10-10.
  */
class SpiderController extends Controller {
  val logger: Logger = Logger(this.getClass)

  def index = Action {
    Ok("It works!")
  }

  def startSpider = Action { request =>
    val body: Option[JsValue] = request.body.asJson
    logger.debug(s"received spider request, body is = ${body.get}")

    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Strict(ByteString("Success"), Some("text/plain"))
    )
  }

}
