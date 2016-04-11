package com.spider.downloader

import akka.actor.{ActorSystem, Props}
import com.spider.core.akka.spring.SpringServiceHelper
import com.spider.downloader.actor.DownloadCoordinatorActor
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

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

}

class DownloaderEngineMaster () {

}
