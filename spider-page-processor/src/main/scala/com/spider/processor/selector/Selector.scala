package com.spider.processor.selector


/**
  * Created by jason on 16-2-25.
  */
trait Selector {
  def select(text: String): String

  def selectList(text: String): List[String]
}
