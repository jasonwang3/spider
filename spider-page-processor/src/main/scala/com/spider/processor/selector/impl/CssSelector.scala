package com.spider.processor.selector.impl

import com.spider.processor.selector.AbstractElementSelector
import org.apache.commons.collections.CollectionUtils
import org.jsoup.nodes.{Element, TextNode}
import scala.collection.JavaConversions._

/**
  * Created by jason on 16-4-13.
  */
class CssSelector(_selectorText: String, _attrName: String) extends AbstractElementSelector {
  val selectorText: String = _selectorText

  val attrName: String = _attrName

  def this(selectorText: String) = this(selectorText, null)

  override def selectElement(element: Element): Element = ???

  override def hasAttribute: Boolean = ???

  override def selectElements(element: Element): List[Element] = ???

  override def select(element: Element): String = {
    val elements: List[Element] = selectElements(element)
    if (elements.isEmpty) {
      return null
    }
    return getValue(elements(0))
  }

  override def selectList(element: Element): List[String] = ???

  private def getValue(element: Element): String = {
    if (attrName == null) {
      return element.outerHtml
    }
    else if ("innerHtml".equalsIgnoreCase(attrName)) {
      return element.html
    }
    else if ("text".equalsIgnoreCase(attrName)) {
      return getText(element)
    }
    else if ("allText".equalsIgnoreCase(attrName)) {
      return element.text
    }
    else {
      return element.attr(attrName)
    }
  }

  protected def getText(element: Element): String = {
    val accum: StringBuilder = new StringBuilder
    for (node <- element.childNodes) {
      if (node.isInstanceOf[TextNode]) {
        val textNode: TextNode = node.asInstanceOf[TextNode]
        accum.append(textNode.text)
      }
    }
    return accum.toString
  }
}
