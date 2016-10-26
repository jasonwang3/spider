package com.spider.model

import com.google.common.collect.{HashBasedTable, Table}
import com.spider.model.downloader.{ProxyPool, Request}
import org.apache.http.HttpHost
import scala.collection.immutable.HashMap
import scala.collection.mutable

/**
  * Created by jason on 2016/1/24.
  */

object Site {

  val DEFAULT_STATUS_CODE_SET = Set[Int](200)
}

@SerialVersionUID(1L)
class Site extends Serializable {

  def this(domain: String) = {
    this()
    this.domain = domain
  }

  var domain: String = null

  var userAgent: String = null

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

  var httpProxyPool: ProxyPool = null

  var useGzip = true

  def toTask(): Task = {
    val task = new Task(this, this.domain)
    task
  }

  def getAllCookies: java.util.Map[String, java.util.Map[String, String]] = {
    this.cookies.rowMap()
  }

  def setCharset(charset: String): Site = {
    this.charset = charset
    this
  }

  def addCookie(name: String, value: String): Site = {
    this.defaultCookies += (name -> value)
    this
  }

  def addCookie(domain: String, name: String, value: String): Site = {
    this.cookies.put(domain, name, value)
    this
  }

  def addHeader(key: String, value: String): Site = {
    this.headers += (key -> value)
    this
  }
}
