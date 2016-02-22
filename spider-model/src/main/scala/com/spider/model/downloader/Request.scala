package com.spider.model.downloader

import scala.collection.mutable


/**
  * Created by jason on 16-1-22.
  */

object Request {
  val CYCLE_TRIED_TIMES = "_cycle_tried_times"

  val STATUS_CODE = "statusCode"

  val PROXY = "proxy"
}


class Request(_url: String) {

  val url: String = _url

  var method: String = null
  /**
    * Store additional information in extras.
    */
  var extras: mutable.HashMap[String, Any] = null

  /**
    * Priority of the request.<br>
    * The bigger will be processed earlier. <br>
    */
  var priority: Long = 0

  def getExtra(key: String): Any = {
    if (extras == null) {
      return null
    }
    return extras.get(key)
  }

  def putExtra(key: String, value: Any): Request = {
    if (extras == null) {
      extras = mutable.HashMap[String, Any]()
    }
    extras += (key -> value)
    return this
  }

}
