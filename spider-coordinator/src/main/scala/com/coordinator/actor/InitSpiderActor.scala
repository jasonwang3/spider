package com.coordinator.actor

import akka.actor.Actor
import com.spider.model.Spider

/**
  * Created by jason on 16-5-10.
  */
class InitSpiderActor extends Actor {
  override def receive: Receive = {
    case spider: Spider => initSpider(spider)
  }


  def initSpider(spider: Spider) = {

  }



}
