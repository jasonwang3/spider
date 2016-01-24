package com.spider.downloader

import akka.actor.ActorSystem
import org.slf4j.LoggerFactory

/**
  * Created by jason on 16-1-21.
  */


object DownloaderEngineMaster extends App {
  val logger = LoggerFactory.getLogger(classOf[DownloaderEngineMaster])
  val system = ActorSystem("ClusterSystem")

}

class DownloaderEngineMaster () {

}
