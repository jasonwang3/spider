package com.spider.processor

import akka.actor.{ActorSystem, Props}
import org.slf4j.LoggerFactory

/**
  * Created by jason on 16-5-17.
  */
object ProcessorMaster extends App {
  val logger = LoggerFactory.getLogger(classOf[ProcessorMaster])
  val system = ActorSystem("ClusterSystem")

}

class ProcessorMaster()
