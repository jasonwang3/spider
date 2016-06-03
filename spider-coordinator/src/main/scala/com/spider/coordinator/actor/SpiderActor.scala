package com.spider.coordinator.actor

import akka.actor.{Actor, ActorLogging}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.{Publish, Subscribe}
import com.spider.model.downloader.{DownloadRequest, Page, Request}
import com.spider.model.processor.{AnalyzeRequest, AnalyzeResponse}
import com.spider.model.{Action, PubSubMessage, Site, Spider}

/**
  * Created by jason on 16-5-13.
  */
class SpiderActor(_spider: Spider) extends Actor with ActorLogging {
  val mediator = DistributedPubSub(context.system).mediator

  val spider: Spider = _spider

  override def receive: Receive = {
    case page: Page => processPage(page)
    case analyzeResponse: AnalyzeResponse => processAnalyzeResponse(analyzeResponse)
    case _ => log.info("received unknown message")

  }

  def sendDownloadRequest(downloadRequest: DownloadRequest) = {
    log.info("sent download request")
    mediator ! Publish(PubSubMessage.DOWNLOAD_REQUEST, downloadRequest)
  }

  def processPage(page: Page): Unit = {
    log.debug("received page, status code is {},step is {}", page.statusCode, page.step)
    val step = page.step
    if (page.statusCode == 200) {
      val rule = spider.rules(step)
      if (rule != null) {
        val action = rule.action
        if (action == Action.Download) {
          val analyzeRequest: AnalyzeRequest = new AnalyzeRequest(spider.id, step, page.rawText, rule, spider.site.domain)
          mediator ! Publish(PubSubMessage.ANALYZE_REQUEST, analyzeRequest)
        }
      } else {
        //TODO
      }
    } else {
      //TODO
    }
  }

  def processAnalyzeResponse(analyzeResponse: AnalyzeResponse) = {
    log.debug("received analyze response {}", analyzeResponse)
    if (spider.rules(analyzeResponse.step) != null) {
      spider.rules(analyzeResponse.step).action match {
        case Action.Download => {
          analyzeResponse.targets.foreach(url => {
            val downloadRequest: DownloadRequest = generateDownloadRequest(new Request(url), spider.site, analyzeResponse.step + 1)
            mediator ! Publish(PubSubMessage.DOWNLOAD_REQUEST, downloadRequest)
          })
        };
        case _ => log.error("Unknown Rule,spider id is {}", spider.id)
      }
    }
  }


  def generateDownloadRequest(request: Request, site: Site, step: Int): DownloadRequest = {
    val downloadRequest: DownloadRequest = new DownloadRequest(request, site, spider.id, step)
    downloadRequest
  }

  override def preStart() = {
    log.info("Spider Actor started, name is {}", self.path.name)
    val downLoadRequest = generateDownloadRequest(spider.request, spider.site, 0)
    sendDownloadRequest(downLoadRequest)
  }
}
