package com.spider.coordinator.actor

import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, ActorLogging, OneForOneStrategy, Props}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Subscribe
import com.spider.model.{PubSubMessage, Spider}

import scala.concurrent.duration._

/**
  * Created by jason on 16-5-10.
  */
class SpiderCoordinatorActor extends Actor with ActorLogging {
  val mediator = DistributedPubSub(context.system).mediator
  mediator ! Subscribe(PubSubMessage.START_REQUEST, self)

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
