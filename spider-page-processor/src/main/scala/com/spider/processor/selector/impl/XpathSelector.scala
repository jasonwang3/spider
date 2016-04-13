package com.spider.processor.selector.impl

import com.spider.processor.selector.AbstractBaseElementSelector
import org.jsoup.nodes.Element
import us.codecraft.xsoup.{XPathEvaluator, Xsoup}

import scala.collection.JavaConversions._

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

  override def hasAttribute: Boolean = {
    xPathEvaluator.hasAttribute
  }

  override def selectElements(element: Element): List[Element] = {
    xPathEvaluator.evaluate(element).getElements.toList
  }

  override def select(element: Element): String = {
    xPathEvaluator.evaluate(element).get()
  }

  override def selectList(element: Element): List[String] = {
    xPathEvaluator.evaluate(element).list().toList
  }
}
