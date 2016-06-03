package com.spider.coordinator.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.spider.model.Spider

/**
  * Created by jason on 16-5-10.
  */
class SpiderCoordinatorActor extends Actor with ActorLogging{

  override def receive: Receive = {
    case spider: Spider => initSpider(spider)
  }


  def initSpider(spider: Spider) = {
    log.info("received spider {}", spider.id)
    context.actorOf(Props(classOf[SpiderActor], spider), "spiderActor" + "-" + spider.id)
  }

  override def preStart() = {
    log.info("SpiderCoordinatorActor started")
  }


}
