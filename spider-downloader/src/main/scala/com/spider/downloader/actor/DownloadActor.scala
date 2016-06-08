package com.spider.downloader.actor

import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging
import com.spider.core.akka.spring.SpringServiceHelper
import com.spider.downloader.{AbstractDownloader, HttpClientDownloader}
import com.spider.model.downloader.{DownloadRequest, Page}

/**
  * Created by jason on 16-4-11.
  */

object DownloadActor {
  def props(spiderId: String, downloadRequest: DownloadRequest, from: ActorRef): Props = Props(classOf[DownloadActor], spiderId, downloadRequest, from)
}

class DownloadActor(_spiderId: String, _downloadRequest: DownloadRequest, _from: ActorRef) extends Actor {
  val spiderId: String = _spiderId
  val downloadRequest: DownloadRequest = _downloadRequest
  val log = Logging(context.system, this)
  var downloader: AbstractDownloader = null

  override def receive: Receive = {
    case _ => log.info("received unknown message")
  }

  def download(downloadRequest: DownloadRequest): Unit = {
    val page: Page = downloader.download(downloadRequest.request, downloadRequest.site.toTask())
    page.step = downloadRequest.step
    _from.tell(page, self)
    context.stop(self)
  }

  override def preStart() = {
    log.debug("DownloadCoordinatorActor start, spider id is {}, step is {}", spiderId, downloadRequest.step)
    downloader = SpringServiceHelper.getBean("httpClientDownloader").asInstanceOf[HttpClientDownloader]
    download(downloadRequest)
  }
}
