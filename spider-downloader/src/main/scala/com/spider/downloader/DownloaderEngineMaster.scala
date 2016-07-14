package com.spider.downloader

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Address, Props}
import akka.cluster.Cluster
import com.spider.core.akka.spring.SpringServiceHelper
import com.spider.downloader.actor.DownloadCoordinatorActor
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by jason on 16-1-21.
  */


object DownloaderEngineMaster extends App {
  val logger = LoggerFactory.getLogger(classOf[DownloaderEngineMaster])
  logger.info("initialize spring configuration...")
  val applicationContext: ApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml")
  SpringServiceHelper.getInstance.setApplicationContext(applicationContext)

  val system = ActorSystem("ClusterSystem")
  val myActor = system.actorOf(Props[DownloadCoordinatorActor], "downloadCoordinator")
  Runtime.getRuntime.addShutdownHook(new AppShutdownHook(system))
}

class DownloaderEngineMaster() {

}

class AppShutdownHook (var system: ActorSystem) extends Thread {
  val logger = LoggerFactory.getLogger(classOf[AppShutdownHook])

  override def run {
    logger.info("Shutting down {}", "spider-downloader")
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
