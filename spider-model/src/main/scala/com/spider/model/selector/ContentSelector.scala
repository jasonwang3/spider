package com.spider.model.selector

import com.spider.model.support.SelectorType._

import scala.collection.mutable

/**
  * Created by jason on 16-6-17.
  */
@SerialVersionUID(1L)
class ContentSelector(_paramName: String, _matchRule: mutable.LinkedHashMap[SelectorType, String]) extends Serializable {
  val paramName: String = _paramName

  val matchRule: mutable.LinkedHashMap[SelectorType, String] = _matchRule
}
