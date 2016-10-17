package com.spider.api.module

/**
  * Created by jason on 16-4-18.
  */

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Address}
import akka.cluster.Cluster
import com.google.inject.AbstractModule
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class StartUpModule extends AbstractModule {
  val logger = LoggerFactory.getLogger(classOf[StartUpModule])

  def configure() = {
    val system = ActorSystem("ClusterSystem")

    Runtime.getRuntime.addShutdownHook(new AppShutdownHook(system))
    logger.info("application start up")
  }
}

class AppShutdownHook (var system: ActorSystem) extends Thread {
  val logger = LoggerFactory.getLogger(classOf[StartUpModule])

  override def run {
    logger.info("Shutting down {}", "spider-coordinator")
    val address: Address = Cluster.get(system).selfAddress
    Cluster.get(system).down(address)
    try {
      Thread.sleep(3000)
    }
    catch {
      case ie: InterruptedException => {
        logger.error("Exception caught while waiting to shut down.", ie)
      }
    }
    system.terminate()
    Await.result(system.whenTerminated, Duration.create(5, TimeUnit.SECONDS))
  }
}
