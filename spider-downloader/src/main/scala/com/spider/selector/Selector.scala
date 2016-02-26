package com.spider.selector


/**
  * Created by jason on 16-2-25.
  */
trait Selector {
  def select(text: String): String

  def selectList(test: String): List[String];
}
