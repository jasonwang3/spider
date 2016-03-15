package com.spider.model.selector

/**
  * Created by jason on 16-2-26.
  */

object RegexResult {
  val EMPTY_RESULT: RegexResult = new RegexResult
}

class RegexResult(_groups: Array[String]) {

  private var groups: Array[String] = _groups

  def this() = this(null)

  def get(groupId: Int): String = {
    if (groupId == null) {
      return null
    }
    groups(groupId)
  }
}



