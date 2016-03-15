package com.spider.downloader.actor

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.event.Logging

/**
  * Created by jason on 16-3-15.
  */
class DownloadCoordinatorActor extends Actor {
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case "test" => log.info("received test")
    case _ => log.info("received unknown message")
  }




}
