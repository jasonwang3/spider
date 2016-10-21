package com.spider.api.actor

import java.util.Date

import akka.actor.{Actor, ActorLogging}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Publish
import akka.event.Logging
import com.alibaba.fastjson.JSON
import com.spider.model.{PubSubMessage, Spider}


/**
  * Created by jason on 16-4-19.
  */
class SpiderServiceActor extends Actor with ActorLogging {
  val mediator = DistributedPubSub(context.system).mediator

  override def receive: Receive = {
    case body: String => startSpider(body)
  }

  def startSpider(body: String) = {
    if (!body.isEmpty) {
      val now: Date = new Date()
      val spider: Spider = JSON.parseObject(body, classOf[Spider])
      //TODO:add validation
      spider.id = now.getTime.toString
      spider.startDate = now
      mediator ! Publish(PubSubMessage.START_REQUEST, spider)
      log.info("send spider to coordinator, id is {}, message is {}, ", spider.id, spider)
    }
  }

  override def preStart() = {
    log.info("SpiderServiceActor started!")
  }


}
