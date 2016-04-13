package com.spider.processor.selector

import com.spider.processor.selector.impl.{RegexSelector, PlainText}

/**
  * Created by jason on 16-2-2.
  */
abstract class AbstractSelectable extends Selectable {

  protected def getSourceTexts: List[String]

  override def css(selector: String): Selectable = {
    $(selector)
  }

  override def css(selector: String, attrName: String): Selectable = {
    $(selector, attrName)
  }

  protected def select(selector: Selector, strings: List[String]): Selectable = {
    val result = for (string <- strings;
                      result = selector.select(string)
                      if (result != null)) yield result

    new PlainText(result)
  }

  protected def selectList(selector: Selector, strings: List[String]) = {
    val results = for (string <- strings;
                       result <- selector.selectList(string)) yield result
    new PlainText(results)
  }

  override def all: List[String] = {
    getSourceTexts
  }

  override def jsonPath(jsonPath: String) = {
    throw new UnsupportedOperationException
  }

  override def get: String = {
    if (!all.isEmpty) {
      return all(0)
    }
    null
  }


  override def select(selector: Selector) = {
    select(selector, getSourceTexts)
  }

  override def selectList(selector: Selector): Selectable = {
    return selectList(selector, getSourceTexts)
  }

  override def regex(regex: String): Selectable = ???

  override def regex(regex: String, group: Int) = ???

  override def regex(regex: String, replacement: String) = ???

  def getFirstSourceText: String = {
    if (!getSourceTexts.isEmpty) {
      return getSourceTexts(0)
    }
    return null
  }

  override def exist: Boolean = getSourceTexts.isEmpty


}
