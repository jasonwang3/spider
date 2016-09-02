package com.spider.processor.actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.alibaba.fastjson.JSONObject
import com.spider.model.{Action, Rule}
import com.spider.model.processor.{AnalyzeRequest, AnalyzeResponse}
import com.spider.model.support.SelectorType
import com.spider.processor.selector.{AbstractSelectable, Selectable}
import com.spider.processor.selector.impl.{Html, HtmlNode, PlainText}
import com.spider.model.Action.Action
import org.apache.commons.collections.CollectionUtils

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
    var analyzeResponse: AnalyzeResponse = null
    if (analyzeRequest.rule.action == Action.GET_URL) {
      val links = generateLinks(html, analyzeRequest.rule)
      analyzeResponse = new AnalyzeResponse(analyzeRequest.spiderId, analyzeRequest.step, links)
    } else if (analyzeRequest.rule.action == Action.GET_CONTENT) {
      val jSONObject = generateContent(html, analyzeRequest.rule)
      val json = jSONObject.toJSONString
      analyzeResponse = new AnalyzeResponse(analyzeRequest.spiderId, analyzeRequest.step, List(json))
    }
    from.tell(analyzeResponse, self)
    log.debug("sent analyze response {}", analyzeResponse)
  }

  def generateLinks(html: Html, rule: Rule): List[String] = {
    var _html: Selectable = html
    rule.matchRule.foreach(matchRule => {
      if (matchRule._1 == SelectorType.CSS) {
        _html = _html.$(matchRule._2)
      } else if (matchRule._1 == SelectorType.XPATH) {
        _html = _html.xpath(matchRule._2)
      } else if (matchRule._1 == SelectorType.LINK) {
        _html = _html.links.asInstanceOf[PlainText]
      } else if (matchRule._1 == SelectorType.REGEX) {
        _html = _html.regex(matchRule._2)
      } else {
        //TODO
      }
    })
    processUrl(_html.all)
  }

  def generateContent(html: Html, rule: Rule): JSONObject = {
    var _html: Selectable = html
    val jsonObjet:JSONObject = new JSONObject()
    rule.matchRule.foreach(matchRule => {
      if (matchRule._1 == SelectorType.CSS) {
        _html = _html.$(matchRule._2)
      } else if (matchRule._1 == SelectorType.XPATH) {
        _html = _html.xpath(matchRule._2)
      } else if (matchRule._1 == SelectorType.REGEX) {
        _html = _html.regex(matchRule._2)
      } else {
        //TODO
      }
    })
    if (rule.contentSelectors != null && rule.contentSelectors.nonEmpty) {
      rule.contentSelectors.foreach(contentSelector => {
        val paramName = contentSelector.paramName
        var list: List[String] = List()
        contentSelector.matchRule.foreach(matchRule => {
          if (matchRule._1 == SelectorType.CSS) {
            _html = _html.$(matchRule._2)
          } else if (matchRule._1 == SelectorType.XPATH) {
            _html = _html.$(matchRule._2)
          } else if (matchRule._1 == SelectorType.REGEX) {
            _html = _html.regex(matchRule._2)
          }
        })
        jsonObjet.put(paramName, _html.all)
      })
    }
    jsonObjet
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
