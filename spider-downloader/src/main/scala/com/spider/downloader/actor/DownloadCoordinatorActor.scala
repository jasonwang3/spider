package com.spider.downloader.actor

import akka.actor.{Actor, ActorLogging}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.{Subscribe, SubscribeAck}
import com.spider.core.akka.spring.SpringServiceHelper
import com.spider.downloader.{AbstractDownloader, HttpClientDownloader}
import com.spider.model.PubSubMessage
import com.spider.model.downloader.DownloadRequest

/**
  * Created by jason on 16-3-15.
  */
class DownloadCoordinatorActor extends Actor with ActorLogging {
  var downloader: AbstractDownloader = null
  val mediator = DistributedPubSub(context.system).mediator

  mediator ! Subscribe(PubSubMessage.DOWNLOAD_REQUEST, self)

  override def receive: Receive = {
    case SubscribeAck(Subscribe(PubSubMessage.DOWNLOAD_REQUEST, None, `self`)) => log.info("subscribing {} topic", PubSubMessage.DOWNLOAD_REQUEST);
    case downloadRequest: DownloadRequest => processRequest(downloadRequest)
    case _ => log.info("received unknown message")
  }

  def processRequest(downloadRequest: DownloadRequest) = {
    val downloadActor = context.actorOf(DownloadActor.props(downloadRequest.spiderId))
    downloadActor.tell(downloadRequest, sender)
  }


  override def preStart() = {
    downloader = SpringServiceHelper.getBean("httpClientDownloader").asInstanceOf[HttpClientDownloader]
    log.info("downloadCoordinatorActor start")
  }
}
