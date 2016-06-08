package com.spider.coordinator.actor

import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, ActorLogging, OneForOneStrategy, Props}
import com.spider.model.Spider

import scala.concurrent.duration._

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

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 3, withinTimeRange = 1 minute) {
      case _: Exception => Stop
    }


  override def preStart() = {
    log.info("SpiderCoordinatorActor started")
  }


}
