package com.coordinator

import akka.actor.{ActorSystem, Props}
import org.slf4j.LoggerFactory

/**
  * Created by jason on 16-5-10.
  */
object CoordinatorMaster extends App {
  val logger = LoggerFactory.getLogger(classOf[CoordinatorMaster])
  val system = ActorSystem("ClusterSystem")

}

class CoordinatorMaster