package com.spider.api.controllers

import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent._
import javax.inject.Singleton

import akka.pattern.AskTimeoutException


/**
  * Created by jason on 16-10-24.
  */
@Singleton
class CommonErrorHandler extends HttpErrorHandler {
  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    Future.successful(
      Status(statusCode)("A client error occurred: " + message)
    )
  }

  def onServerError(request: RequestHeader, exception: Throwable) = {
    Future.successful(
      exception match {
        case ex:AskTimeoutException => InternalServerError("A server timeout error")
        case ex:Exception => InternalServerError("A server error occurred")
      }
    )
  }
}
