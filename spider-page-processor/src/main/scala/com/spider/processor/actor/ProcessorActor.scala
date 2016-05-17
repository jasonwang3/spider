package com.spider.processor.actor

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, Props}
import com.spider.model.Rule
import com.spider.model.processor.AnalyzeRequest
import com.spider.model.support.SelectorType
import com.spider.processor.selector.impl.Html

/**
  * Created by jason on 16-5-17.
  */
object ProcessorActor {
  def props(spiderId: String): Props = Props(classOf[ProcessorActor], spiderId)
}


class ProcessorActor(_spiderId: String) extends Actor with ActorLogging {
  val spiderId = _spiderId

  override def receive: Receive = {
    case analyzeRequest: AnalyzeRequest => processAnalyzeRequest(analyzeRequest)
    case _ => log.warning("received unknown message")
  }


  def processAnalyzeRequest(analyzeRequest: AnalyzeRequest) = {
    val html: Html = Html(analyzeRequest.htmlRaw)
    val links = generateLinks(html, analyzeRequest.rule)
    links
  }

  def generateLinks(html: Html, rule: Rule): List[String] = {
    var _html = html
    rule.matchRule.foreach(matchRule => {
      if (matchRule._1 == SelectorType.CSS) {
        _html = _html.css(matchRule._2).asInstanceOf
      } else if (matchRule._1 == SelectorType.XPATH) {
        _html = _html.xpath(matchRule._2).asInstanceOf
      } else if (matchRule._1 == SelectorType.LINK) {
        _html = _html.links.asInstanceOf
      }
    })
    _html.all
  }


  override def preStart() = {
    log.info("ProcessorActor started, spider id is {}", spiderId)
  }


}
