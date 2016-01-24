package com.spider.model.downloader

/**
  * Created by jason on 16-1-22.
  */

object Request {
  val CYCLE_TRIED_TIMES = "_cycle_tried_times"

  val STATUS_CODE = "statusCode"

  val PROXY = "proxy"
}


class Request {

  var url: String = ""

  var method: String = ""
  /**
    * Store additional information in extras.
    */
  var extra: Map[String, Any] = Map()

  /**
    * Priority of the request.<br>
    * The bigger will be processed earlier. <br>
    */
  var priority: Long = 0

}
