package com.spider.model.selector

import com.spider.model.MatchRule

/**
  * Created by jason on 16-6-17.
  */
@SerialVersionUID(1L)
class ContentSelector(val paramName: String, val matchRule: List[MatchRule]) extends Serializable {
  var urlDownload = false
}
