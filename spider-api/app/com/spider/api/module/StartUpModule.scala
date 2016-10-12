package com.spider.api.module

/**
  * Created by jason on 16-4-18.
  */

import akka.actor.ActorSystem
import com.google.inject.AbstractModule
import org.slf4j.LoggerFactory

class StartUpModule extends AbstractModule {
  val logger = LoggerFactory.getLogger(classOf[StartUpModule])

  def configure() = {
    val system = ActorSystem("ClusterSystem")


    logger.info("application start up")
  }
}
