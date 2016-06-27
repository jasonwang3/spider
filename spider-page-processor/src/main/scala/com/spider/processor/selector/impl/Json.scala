package com.spider.processor.selector.impl

import com.spider.processor.selector.Selectable
import us.codecraft.xsoup.XTokenQueue

/**
  * Created by jason on 16-3-11.
  */

object Json {


}

class Json(_sourceTexts: List[String]) extends PlainText(_sourceTexts){

  def this(text: String) = this(List(text))

  def removePadding(padding: String): Json = {
    val text = getFirstSourceText
    var xTokenQueue: XTokenQueue = new XTokenQueue(text)
    xTokenQueue.consumeWhitespace
    xTokenQueue.consume(padding)
    xTokenQueue.consumeWhitespace
    val chompBalanced: String = xTokenQueue.chompBalancedNotInQuotes('(', ')')
    new Json(chompBalanced)
  }

//  override def jsonPath(jsonPath: String): Selectable = {
////    val jsonPathSelector: JsonPathSelector = new JsonPathSelector(jsonPath)
//    return selectList(jsonPathSelector, getSourceTexts)
//  }
  override def srcs: Selectable = ???
}


