package com.spider.processor.selector.impl

import java.util.Collections

import com.spider.processor.selector.{ElementSelector, Selector}
import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element, Entities}
import scala.collection.JavaConversions._

/**
  * Created by jason on 16-3-8.
  */

object Html {
  var INITED: Boolean = false

  var DISABLE_HTML_ENTITY_ESCAPE: Boolean = true

  def disableJsoupHtmlEntityEscape = {
    if (Html.DISABLE_HTML_ENTITY_ESCAPE && !Html.INITED) {
      Entities.EscapeMode.base.getMap.clear
      Entities.EscapeMode.extended.getMap.clear
      Html.INITED = true
    }
  }

  def apply(text: String): Html = {
    try {
      disableJsoupHtmlEntityEscape
      var document = Jsoup.parse(text)
      new Html(document)
    }
    catch {
      case e: Exception => {
        new Html()
      }
    }
  }

  def create(text: String): Html = {
    Html(text)
  }
}

class Html(val _document: Document) extends HtmlNode {
  private val document: Document = _document

  def this() = this(null)


  def getDocument: Document = document

  def selectDocument(selector: Selector): String = {
    if (selector.isInstanceOf[ElementSelector]) {
      val elementSelector: ElementSelector = selector.asInstanceOf[ElementSelector]
      return elementSelector.select(document)
    } else {
      selector.select(getFirstSourceText)
    }
  }

  def selectDocumentForList(selector: Selector): List[String] = {
    if (selector.isInstanceOf[ElementSelector]) {
      val elementSelector: ElementSelector = selector.asInstanceOf[ElementSelector]
      return elementSelector.selectList(getDocument)
    }
    else {
      return selector.selectList(getFirstSourceText)
    }
  }


  override def getElements: List[Element] = {
    return Collections.singletonList[Element](getDocument).toList
  }

}
