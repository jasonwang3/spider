package com.spider.selector

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

//  protected def select(selector: Selector, strings: List[String]): Selectable = {
//    for (string <- strings;
//         result = selector.select(string);
//         if (result != null)) yield result
//  }

}
