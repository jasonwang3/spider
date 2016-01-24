package com.spider.model.downloader

import com.google.common.collect.{HashBasedTable, Table}

import scala.collection.immutable.HashMap
import scala.collection.mutable

/**
  * Created by jason on 2016/1/24.
  */

object Site {

  val DEFAULT_STATUS_CODE_SET = Set[Int]()


}


class Site {

  var domain = ""

  var userAgent = ""

  var defaultCookies = mutable.LinkedHashMap[String, String]

  var cookies: Table[String, String, String] = HashBasedTable.create()

  var charset = ""

  var startRequests = Array[Request]()

  var sleepTime = 5000

  var retryTimes = 0

  var cycleRetryTimes = 0

  var retrySleepTime = 1000

  var timeOut = 5000

  var acceptStatCode: Set[Int] = Site.DEFAULT_STATUS_CODE_SET

  var headers = HashMap[String, String]

}
