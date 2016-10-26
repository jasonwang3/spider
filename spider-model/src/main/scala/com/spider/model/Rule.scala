package com.spider.model

import com.spider.model.Action.Action
import com.spider.model.selector.ContentSelector

import scala.collection.immutable.ListMap


/**
  *
  * @param action
  * @param matchRule CSS -> "div.bDiv", XPATH -> frame[@id='detailMainFrame']
  */
@SerialVersionUID(1L)
class Rule(val action: Action, val matchRule: List[MatchRule], var contentSelectors: List[ContentSelector]) extends Serializable {
  def this(action: Action, matchRule: List[MatchRule]) = {
    this(action, matchRule, null)
  }

  var rule: Rule = null
}
