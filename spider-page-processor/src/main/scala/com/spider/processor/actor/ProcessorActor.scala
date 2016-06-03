package com.spider.processor.actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.spider.model.Rule
import com.spider.model.processor.{AnalyzeRequest, AnalyzeResponse}
import com.spider.model.support.SelectorType
import com.spider.processor.selector.AbstractSelectable
import com.spider.processor.selector.impl.{Html, HtmlNode, PlainText}

/**
  * Created by jason on 16-5-17.
  */
object ProcessorActor {
  def props(spiderId: String, analyzeRequest: AnalyzeRequest, from: ActorRef): Props = Props(classOf[ProcessorActor], spiderId, analyzeRequest, from)
}


class ProcessorActor(_spiderId: String, _analyzeRequest: AnalyzeRequest, _from: ActorRef) extends Actor with ActorLogging {
  val spiderId = _spiderId
  val analyzeRequest = _analyzeRequest
  val from = _from

  override def receive: Receive = {
    case _ => log.warning("received unknown message")
  }


  def processAnalyzeRequest(analyzeRequest: AnalyzeRequest) = {
    val html: Html = Html(analyzeRequest.htmlRaw)
    val links = generateLinks(html, analyzeRequest.rule)
    val analyzeResponse = new AnalyzeResponse(analyzeRequest.spiderId, analyzeRequest.step, links)
    from.tell(analyzeResponse, self)
    log.debug("sent analyze response {}", analyzeResponse)
  }

  def generateLinks(html: Html, rule: Rule): List[String] = {
    var _html: AbstractSelectable = html
    rule.matchRule.foreach(matchRule => {
      if (matchRule._1 == SelectorType.CSS) {
        _html = _html.$(matchRule._2).asInstanceOf[HtmlNode]
      } else if (matchRule._1 == SelectorType.XPATH) {
        _html = _html.xpath(matchRule._2).asInstanceOf[PlainText]
      } else if (matchRule._1 == SelectorType.LINK) {
        _html = _html.links.asInstanceOf[PlainText]
      } else if (matchRule._1 == SelectorType.REGEX) {
        _html = _html.regex(matchRule._2).asInstanceOf[PlainText]
      } else {
        //TODO
      }
    })
    return processUrl(_html.all)
  }


  override def preStart() = {
    log.info("ProcessorActor started, spider id is {}", spiderId)
    processAnalyzeRequest(analyzeRequest)
  }


  def processUrl(urls: List[String]): List[String] = {
    var valueList: List[String] = List()
    urls.foreach(url => {
      if (!url.startsWith("http://") && !url.startsWith("https://")) {
        valueList = "http://" + analyzeRequest.domain + url :: valueList
      }
    })
    valueList.reverse
  }

}
