package com.spider.selector.impl

import com.spider.selector.AbstractBaseElementSelector
import org.apache.commons.collections.CollectionUtils
import org.jsoup.nodes.Element
import us.codecraft.xsoup.{Xsoup, XPathEvaluator}

import scala.beans.BeanProperty
import scala.reflect.internal.util.Collections

/**
  * Created by jason on 16-3-3.
  */
class XpathSelector(_xpathStr: String) extends AbstractBaseElementSelector {
  var xPathEvaluator: XPathEvaluator = Xsoup.compile(_xpathStr)

  override def selectElement(element: Element): Element = {
    val elements: List[Element] = selectElements(element)
    if (elements != null && !elements.isEmpty) return elements(0)
    null
  }

  override def hasAttribute: Boolean = ???

  override def selectElements(element: Element): List[Element] = ???

  override def select(element: Element): String = ???

  override def selectList(element: Element): List[String] = ???
}
