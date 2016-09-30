package com.spider.processor

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Address, Props}
import akka.cluster.Cluster
import com.spider.processor.actor.ProcessorCoordinatorActor
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by jason on 16-5-17.
  */
object ProcessorMaster extends App {
  val logger = LoggerFactory.getLogger(classOf[ProcessorMaster])
  val system = ActorSystem("ClusterSystem")

  val myActor = system.actorOf(ProcessorCoordinatorActor.props, "spider-processor")

  Runtime.getRuntime.addShutdownHook(new AppShutdownHook(system))
}

class ProcessorMaster()

class AppShutdownHook (var system: ActorSystem) extends Thread {
  val logger = LoggerFactory.getLogger(classOf[AppShutdownHook])

  override def run {
    logger.info("Shutting down {}", "pageProcessor")
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
