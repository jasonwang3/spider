package com.spider.selector.impl

import com.spider.selector.{Selectable, AbstractSelectable}
import org.jsoup.nodes.Element

/**
  * Created by jason on 16-3-2.
  */
class HtmlNode(_element: List[Element]) extends AbstractSelectable {

  val element: List[Element] = _element

  def this() = this(null)


  override protected def getSourceTexts: List[String] = ???

  override def links: Selectable = xpath("//a/@href")

  override def nodes: List[Selectable] = ???

  override def smartContent: Selectable = {
    val smartContentSelector: SmartContentSelector = new SmartContentSelector
    select(smartContentSelector, getSourceTexts)
  }

  override def xpath(xpath: String): Selectable = ???

  override def $(selector: String): Selectable = ???

  override def $(selector: String, attrName: String): Selectable = ???
}
