package com.spider.downloader.actor

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.event.Logging
import com.spider.core.akka.spring.SpringServiceHelper
import com.spider.downloader.{AbstractDownloader, HttpClientDownloader}
import com.spider.model.downloader.DownloadRequest
import com.spider.selector.impl.Page

/**
  * Created by jason on 16-3-15.
  */
class DownloadCoordinatorActor extends Actor {
  val log = Logging(context.system, this)
  var downloader: AbstractDownloader = null

  override def receive: Receive = {
    case downloadRequest: DownloadRequest => processRequest(downloadRequest)
    case page: Page => processPage(page)
    case _ => log.info("received unknown message")
  }

  def processRequest(downloadRequest: DownloadRequest) = {

  }

  def processPage(page: Page) = {

  }


  override def preStart() = {
    downloader = SpringServiceHelper.getBean("httpClientDownloader").asInstanceOf[HttpClientDownloader]
    log.info("downloadCoordinatorActor start")
  }
}
