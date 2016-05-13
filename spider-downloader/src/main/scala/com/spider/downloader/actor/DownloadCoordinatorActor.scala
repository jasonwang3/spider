package com.spider.downloader.actor

import akka.actor.{Actor, Props}
import akka.actor.Actor.Receive
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.{Subscribe, SubscribeAck}
import akka.event.Logging
import com.spider.core.akka.spring.SpringServiceHelper
import com.spider.downloader.{AbstractDownloader, HttpClientDownloader}
import com.spider.model.downloader.{DownloadRequest, Page}

/**
  * Created by jason on 16-3-15.
  */
class DownloadCoordinatorActor extends Actor {
  val log = Logging(context.system, this)
  var downloader: AbstractDownloader = null
  val mediator = DistributedPubSub(context.system).mediator

  mediator ! Subscribe("download", self)


  override def receive: Receive = {
    case SubscribeAck(Subscribe("content", None, `self`)) => log.info("subscribing");
    case downloadRequest: DownloadRequest => processRequest(downloadRequest)
    case _ => log.info("received unknown message")
  }

  def processRequest(downloadRequest: DownloadRequest) = {
    val downloadActor = context.actorOf(Props[DownloadActor], "downloadActor-" + downloadRequest.spiderId)
    downloadActor.tell(downloadActor, sender)
  }


  override def preStart() = {
    downloader = SpringServiceHelper.getBean("httpClientDownloader").asInstanceOf[HttpClientDownloader]
    log.info("downloadCoordinatorActor start")
  }
}
