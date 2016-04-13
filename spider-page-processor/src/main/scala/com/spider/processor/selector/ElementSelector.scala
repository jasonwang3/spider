package com.spider.processor.selector

import org.jsoup.nodes.Element


/**
  * Created by jason on 16-2-26.
  */
trait ElementSelector {

  def select(element: Element): String

  def selectList(element: Element): List[String]
}
