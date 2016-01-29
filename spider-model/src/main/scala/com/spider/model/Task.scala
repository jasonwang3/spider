package com.spider.model

import java.util.UUID

/**
  * Created by jason on 16-1-28.
  */
class Task {

  var uuid = generateUUID("123")

  var site: Site = null

  def generateUUID(x: String) = {
    if (x != null) {
      x
    } else {
      UUID.randomUUID().toString
    }
  }
}
