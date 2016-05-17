package com.spider.downloader.actor

import akka.actor.{Actor, Props}
import akka.event.Logging
import com.spider.core.akka.spring.SpringServiceHelper
import com.spider.downloader.{AbstractDownloader, HttpClientDownloader}
import com.spider.model.downloader.{DownloadRequest, Page}

/**
  * Created by jason on 16-4-11.
  */

object DownloadActor {
  def props(spiderId: String): Props = Props(classOf[DownloadActor], spiderId)
}

class DownloadActor(_spiderId: String) extends Actor {
  val spiderId: String = _spiderId
  val log = Logging(context.system, this)
  var downloader: AbstractDownloader = null

  override def receive: Receive = {
    case downloadRequest: DownloadRequest => download(downloadRequest)
    case _ => log.info("received unknown message")
  }

  def download(downloadRequest: DownloadRequest): Unit = {
    log.info("received download request,url is {}", downloadRequest.request.url)
    val page: Page = downloader.download(downloadRequest.request, downloadRequest.site.toTask())
    page.step = downloadRequest.step
    sender().tell(page, self)
  }

  override def preStart() = {
    downloader = SpringServiceHelper.getBean("httpClientDownloader").asInstanceOf[HttpClientDownloader]
    log.info("downloadCoordinatorActor start, spider id is {}", spiderId)
  }
}
