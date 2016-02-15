package com.spider.selector

/**
  * Created by jason on 16-2-2.
  */
trait Selectable {

  def xpath(xpath: String): Selectable

}
