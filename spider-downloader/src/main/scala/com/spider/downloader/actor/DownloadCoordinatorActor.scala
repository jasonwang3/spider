package com.spider.downloader.actor

import java.net.SocketTimeoutException

import akka.actor.SupervisorStrategy.{Directive, Restart, Stop}
import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.{Subscribe, SubscribeAck}
import com.spider.core.akka.spring.SpringServiceHelper
import com.spider.downloader.{AbstractDownloader, HttpClientDownloader}
import com.spider.model.PubSubMessage
import com.spider.model.downloader.DownloadRequest
import com.spider.model.message.DownloadURL
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}

import scala.concurrent.duration._

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
    case downloadURL: DownloadURL => download(downloadURL)
    case _ => log.info("received unknown message")
  }

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 3, withinTimeRange = 1 minute) {
      case _: SocketTimeoutException => Restart
      case _: Exception => Stop
    }


  def processRequest(downloadRequest: DownloadRequest) = {
    context.actorOf(DownloadActor.props(downloadRequest.spiderId, downloadRequest, sender()))
    log.debug("created download actor to download, spider id is {}", downloadRequest.spiderId)
  }

  def download(downloadURL: DownloadURL) = {
    val closeableHttpClient: CloseableHttpClient = HttpClients.createDefault()
    val httpGet =  new HttpGet()
    httpGet.setHeader("User-Agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
  }

  override def preStart() = {
    downloader = SpringServiceHelper.getBean("httpClientDownloader").asInstanceOf[HttpClientDownloader]
    log.info("downloadCoordinatorActor start")
  }
}


