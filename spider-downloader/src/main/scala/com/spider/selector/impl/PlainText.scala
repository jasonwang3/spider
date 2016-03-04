package com.spider.selector.impl

import com.spider.selector.{AbstractSelectable, Selectable, Selector}

/**
  * Created by jason on 16-2-26.
  */

object PlainText {
  def create(text: String): PlainText = {
    new PlainText(text)
  }
}

class PlainText(_sourceTexts: List[String]) extends AbstractSelectable {

  var sourceTexts: List[String] = _sourceTexts

  def this(text: String) = this(List(text))


  override def links: Selectable = {
    throw new UnsupportedOperationException("Links can not apply to plain text. Please check whether you use a previous xpath with attribute select (/@href etc).")
  }

  override def nodes: List[Selectable] = {
    for (string <- sourceTexts;
         nodes = PlainText.create(string)) yield nodes

  }

  override def xpath(xpath: String): Selectable = {
    throw new UnsupportedOperationException("XPath can not apply to plain text. Please check whether you use a previous xpath with attribute select (/@href etc).")
  }

  override def $(selector: String): Selectable = {
    throw new UnsupportedOperationException("$ can not apply to plain text. Please check whether you use a previous xpath with attribute select (/@href etc).")
  }

  override def $(selector: String, attrName: String): Selectable = {
    throw new UnsupportedOperationException("$ can not apply to plain text. Please check whether you use a previous xpath with attribute select (/@href etc).")
  }

  override def smartContent: Selectable = {
    throw new UnsupportedOperationException("Smart content can not apply to plain text. Please check whether you use a previous xpath with attribute select (/@href etc).")
  }

  override protected def getSourceTexts: List[String] = sourceTexts
}
