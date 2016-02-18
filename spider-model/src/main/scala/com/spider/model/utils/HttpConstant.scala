package com.spider.model.utils

/**
  * Created by jason on 16-2-18.
  */
object HttpConstant {

  object Method {
    val GET = "GET"
    val HEAD: String = "HEAD"
    val POST: String = "POST"
    val PUT: String = "PUT"
    val DELETE: String = "DELETE"
    val TRACE: String = "TRACE"
    val CONNECT: String = "CONNECT"
  }

  object Header {
    val REFERER: String = "Referer"
    val USER_AGENT: String = "User-Agent"
  }

}

