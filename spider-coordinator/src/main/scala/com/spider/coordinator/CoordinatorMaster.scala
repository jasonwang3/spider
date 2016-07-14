package com.spider.coordinator

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Address, Props}
import akka.cluster.Cluster
import com.spider.coordinator.actor.SpiderCoordinatorActor
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by jason on 16-5-10.
  */
object CoordinatorMaster extends App {
  val logger = LoggerFactory.getLogger(classOf[CoordinatorMaster])
  val system = ActorSystem("ClusterSystem")
  system.actorOf(Props[SpiderCoordinatorActor], "spiderCoordinatorActor")
  Runtime.getRuntime.addShutdownHook(new AppShutdownHook(system))
}

class CoordinatorMaster

class AppShutdownHook (var system: ActorSystem) extends Thread {
  val logger = LoggerFactory.getLogger(classOf[AppShutdownHook])

  override def run {
    logger.info("Shutting down {}", "spider-coordinator")
    val address: Address = Cluster.get(system).selfAddress
    Cluster.get(system).down(address)
    Cluster.get(system).leave(address)
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
