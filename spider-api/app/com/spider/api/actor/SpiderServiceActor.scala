package com.spider.api.actor

import java.util.Date

import akka.actor.{Actor, ActorLogging}
import akka.cluster.pubsub.DistributedPubSub
import com.fasterxml.jackson.databind.SerializationFeature
import com.spider.model.support.SelectorType
import com.spider.model.{Action, Spider}
import org.json4s._
import org.json4s.ext.EnumNameSerializer
import org.json4s.jackson.JsonMethods
import org.json4s.jackson.JsonMethods._

/**
  * Created by jason on 16-4-19.
  */
class SpiderServiceActor extends Actor with ActorLogging {
  val mediator = DistributedPubSub(context.system).mediator
  JsonMethods.mapper.configure(SerializationFeature.CLOSE_CLOSEABLE, false)
  implicit val formats = DefaultFormats + new EnumNameSerializer(Action) + new EnumNameSerializer(SelectorType)

  override def receive: Receive = {
    case body: String => startSpider(body)
  }

  def startSpider(body: String) = {
    try {
      log.debug("received message {}", body)
      val now: Date = new Date()
      val jsValue: JValue = parse(body)
      val spider = jsValue.extract[Spider]
      println(spider)
      //TODO:add validation
      //      spider.id = now.getTime.toString
      //      spider.startDate = now
      //      mediator ! Publish(PubSubMessage.START_REQUEST, spider)
      //      log.info("send spider to coordinator, id is {}, message is {}, ", spider.id, spider)
    } catch {
      case ex: MappingException => {
        log.error(ex, "json converter error")
        sender ! ex
      }
      case ex:Exception => {
        log.error(ex, "system occurs error")
        sender ! ex
      }
    }
    sender ! "OK"

  }

  override def preStart() = {
    log.info("SpiderServiceActor started!")
  }


}
