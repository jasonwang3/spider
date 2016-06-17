package com.spider.model.selector

import com.spider.model.support.SelectorType._

/**
  * Created by jason on 16-6-17.
  */
@SerialVersionUID(1L)
class ContentSelector(_paramName: String, _selectorType: SelectorType) extends Serializable{
  val paramName: String = _paramName

  val selectorType: SelectorType = _selectorType
}
