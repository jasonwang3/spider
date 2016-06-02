package com.spider.processor.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.{Subscribe, SubscribeAck}
import com.spider.model.PubSubMessage
import com.spider.model.processor.AnalyzeRequest

/**
  * Created by jason on 16-5-17.
  */
object ProcessorCoordinatorActor {
  def props: Props = Props(classOf[ProcessorCoordinatorActor])
}

class ProcessorCoordinatorActor extends Actor with ActorLogging {

  val mediator = DistributedPubSub(context.system).mediator

  mediator ! Subscribe(PubSubMessage.ANALYZE_REQUEST, self)

  override def receive: Receive = {
    case analyzeRequest: AnalyzeRequest => processAnalyzeRequest(analyzeRequest)
    case SubscribeAck(Subscribe(PubSubMessage.ANALYZE_REQUEST, None, `self`)) => log.info("subscribing {} topic", PubSubMessage.ANALYZE_REQUEST)
    case _ => log.info("received unknown message")
  }

  override def preStart = {
    log.info("ProcessorCoordinatorActor stated")
  }

  def processAnalyzeRequest(analyzeRequest: AnalyzeRequest) = {
    val processorActor = context.actorOf(ProcessorActor.props(analyzeRequest.spiderId, analyzeRequest))
    log.debug("create processor actor to analyze, spider id is {}", analyzeRequest.spiderId)
  }
}
