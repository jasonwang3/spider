package com.spider.processor.actor

import akka.actor.{Actor, ActorLogging}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.{Subscribe, SubscribeAck}
import com.spider.model.PubSubMessage

/**
  * Created by jason on 16-5-17.
  */
class ProcessorCoordinatorActor extends Actor with ActorLogging {

  val mediator = DistributedPubSub(context.system).mediator

  mediator ! Subscribe(PubSubMessage.ANALYZE_REQUEST, self)

  override def receive: Receive = {
    case SubscribeAck(Subscribe(PubSubMessage.ANALYZE_REQUEST, None, `self`)) => log.info("subscribing {} topic", PubSubMessage.ANALYZE_REQUEST)
    case _ => log.info("received unknown message")
  }

  override def preStart = {
    log.info("ProcessorCoordinatorActor stated")

  }
}
