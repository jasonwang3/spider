package com.spider.model

import com.spider.model.support.SelectorType.SelectorType

import scala.collection.mutable

/**
  * Created by jason on 16-4-19.
  */
@SerialVersionUID(1L)
class Rule extends Serializable{
  // CSS -> "div.bDiv", XPATH -> frame[@id='detailMainFrame']
  var matchRule: mutable.LinkedHashMap[SelectorType, String] = null



}
