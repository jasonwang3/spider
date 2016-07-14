package com.spider.model

import com.spider.model.Action.Action
import com.spider.model.selector.ContentSelector
import com.spider.model.support.SelectorType.SelectorType

import scala.collection.mutable

/**
  * Created by jason on 16-4-19.
  */
@SerialVersionUID(1L)
class Rule(_action: Action, _matchRule: mutable.LinkedHashMap[SelectorType, String]) extends Serializable {

  val action: Action = _action

  // CSS -> "div.bDiv", XPATH -> frame[@id='detailMainFrame']
  val matchRule: mutable.LinkedHashMap[SelectorType, String] = _matchRule

  var contentSelectors: List[ContentSelector] = null

  var rule: Rule = null


}
