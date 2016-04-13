package com.spider.processor.selector

import org.apache.commons.lang3.StringUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

/**
  * Created by jason on 16-3-3.
  */
abstract class AbstractBaseElementSelector extends Selector with ElementSelector {

  override def select(text: String): String = {
    if (StringUtils.isNotBlank(text)) {
      return select(Jsoup.parse(text))
    }
    null
  }

  override def selectList(text: String): List[String] = {
    if (StringUtils.isNoneBlank(text)) {
      return selectList(Jsoup.parse(text))
    }
    List()
  }

  def selectElement(text: String): Element = {
    if (StringUtils.isNotBlank(text)) {
      return selectElement(Jsoup.parse(text))
    }
    null
  }


  def selectElements(text: String): List[Element] = {
    if (StringUtils.isNotBlank(text)) {
      return selectElements(Jsoup.parse(text))
    }
    null
  }

  def selectElement(element: Element): Element

  def selectElements(element: Element): List[Element]

  def hasAttribute: Boolean

}

