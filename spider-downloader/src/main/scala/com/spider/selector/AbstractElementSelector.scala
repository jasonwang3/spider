package com.spider.selector

import org.jsoup.Jsoup
import org.jsoup.nodes.Element

/**
  * Created by jason on 16-2-26.
  */
abstract class AbstractElementSelector extends Selector with ElementSelector {

  override def select(text: String): String = {
    if (!text.isEmpty) {
      return select(Jsoup.parse(text))
    }
    null
  }

  override def selectList(text: String): List[String] = {
    if (text.isEmpty) {
      return List[String]()
    }
    selectList(Jsoup.parse(text))
  }

  def selectElement(text: String): Element = {
    if (!text.isEmpty) {
      return selectElement(Jsoup.parse(text))
    }
    null
  }

  def selectElements(text: String): List[Element] = {
    if (!text.isEmpty) {
      return selectElements(Jsoup.parse(text))
    }
    return List[Element]()
  }

  def selectElement(element: Element): Element

  def selectElements(element: Element): List[Element]

  def hasAttribute: Boolean

}
