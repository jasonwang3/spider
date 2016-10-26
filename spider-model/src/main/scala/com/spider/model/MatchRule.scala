package com.spider.model

import com.spider.model.support.SelectorType.SelectorType

/**
  * Created by jason on 16-10-26.
  */
class MatchRule(val selectorType: SelectorType, val rule: String) extends Serializable {

}
