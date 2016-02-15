package com.spider.model

import com.google.common.collect.{HashBasedTable, Table}
import com.spider.model.downloader.Request
import org.apache.http.HttpHost

import scala.collection.immutable.HashMap
import scala.collection.mutable

/**
  * Created by jason on 2016/1/24.
  */

object Site {

  val DEFAULT_STATUS_CODE_SET = Set[Int]()


}

class Site() {

  var domain = ""

  var userAgent = ""

  var defaultCookies = mutable.LinkedHashMap[String, String]()

  var cookies: Table[String, String, String] = HashBasedTable.create()

  var charset = "utf-8"

  var startRequests = Array[Request]()

  var sleepTime = 5000

  var retryTimes = 0

  var cycleRetryTimes = 0

  var retrySleepTime = 1000

  var timeOut = 5000

  var acceptStatCode: Set[Int] = Site.DEFAULT_STATUS_CODE_SET

  var headers = HashMap[String, String]()

  var httpProxy: HttpHost = null

  var useGzip = true

  def toTask(): Task = {
    val task = new Task
    task.site = this
    task.uuid = this.domain
    task
  }

  def setDomain(domain: String): Site = {
    this.domain = domain
    this
  }


}
