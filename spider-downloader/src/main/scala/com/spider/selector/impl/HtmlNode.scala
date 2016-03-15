package com.spider.selector.impl

import java.util

import com.spider.selector.{AbstractBaseElementSelector, AbstractSelectable, Selectable}
import org.jsoup.nodes.{Document, Element}
import scala.collection.JavaConversions._
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  * Created by jason on 16-3-2.
  */
class HtmlNode(_element: List[Element]) extends AbstractSelectable {

  val elements: List[Element] = _element

  def this() = this(null)


  override protected def getSourceTexts: List[String] = ???

  override def links: Selectable = xpath("//a/@href")

  override def nodes: List[Selectable] = {
    var selectables: ArrayBuffer[Selectable] = ArrayBuffer()
    elements.foreach(element => {
      val childElements: ArrayBuffer[Element] = ArrayBuffer(element)
      selectables.addAll(selectables)
    })
    selectables.toList
  }

  override def smartContent: Selectable = {
    val smartContentSelector: SmartContentSelector = new SmartContentSelector
    select(smartContentSelector, getSourceTexts)
  }

  override def xpath(xpath: String): Selectable = {
    val xpathSelector: XpathSelector = new XpathSelector(xpath)
    selectElements(xpathSelector)
  }

  override def $(selector: String): Selectable = ???

  override def $(selector: String, attrName: String): Selectable = ???

  protected def selectElements(elementSelector: AbstractBaseElementSelector): Selectable = {
    var elementIterator: util.ListIterator[Element] = elements.listIterator()
    if (!elementSelector.hasAttribute) {
      val resultElements: ArrayBuffer[Element] = ArrayBuffer()
      while (elementIterator.hasNext) {
        val element: Element = checkElementAndConvert(elementIterator)
        val selectElements: util.List[Element] = elementSelector.selectElements(element)
        resultElements.addAll(selectElements)
      }
      return new HtmlNode(resultElements.toList)
    }
    else {
      val resultStrings: ArrayBuffer[String] = ArrayBuffer()
      while (elementIterator.hasNext) {
        val element: Element = checkElementAndConvert(elementIterator)
        val selectList: util.List[String] = elementSelector.selectList(element)
        resultStrings.addAll(selectList)
      }
      return new PlainText(resultStrings.toList)
    }
  }

  private def checkElementAndConvert(elementIterator: util.ListIterator[Element]): Element = {
    val element: Element = elementIterator.next
    if (!(element.isInstanceOf[Document])) {
      val root: Document = new Document(element.ownerDocument.baseUri)
      val clone: Element = element.clone
      root.appendChild(clone)
      elementIterator.set(root)
      return root
    }
    return element
  }
}
