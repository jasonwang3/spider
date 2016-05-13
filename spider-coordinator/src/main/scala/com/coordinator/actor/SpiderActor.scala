package com.coordinator.actor

import akka.actor.Actor
import com.spider.model.Action._
import com.spider.model.downloader.{DownloadRequest, Request}
import com.spider.model.{Rule, Site, Spider}

import scala.collection.mutable

/**
  * Created by jason on 16-5-13.
  */
class SpiderActor extends Actor {
  var spider: Spider = null

  override def receive: Receive = {
    case spider: Spider => processSpider(spider)
  }


  def processSpider(spider: Spider) = {
    this.spider = spider
    val downloadRequest = generateDownloadRequest(spider.request, spider.site)
  }


  def generateDownloadRequest(request: Request, site: Site): DownloadRequest = {
    val downloadRequest: DownloadRequest = new DownloadRequest(request, site, spider.id)
    downloadRequest
  }
}
