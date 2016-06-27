package com.spider.processor

import akka.actor.{ActorSystem, Props}
import com.spider.processor.actor.ProcessorCoordinatorActor
import org.slf4j.LoggerFactory

/**
  * Created by jason on 16-5-17.
  */
object ProcessorMaster extends App {
  val logger = LoggerFactory.getLogger(classOf[ProcessorMaster])
  val system = ActorSystem("ClusterSystem")

  val myActor = system.actorOf(ProcessorCoordinatorActor.props, "processorCoordinator")
}

class ProcessorMaster()
