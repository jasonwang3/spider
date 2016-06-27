package com.spider.model.downloader

import scala.collection.mutable

/**
  * Created by wxy on 16/2/22.
  */
@SerialVersionUID(1L)
class ResultItems extends Serializable{

  var fields: mutable.LinkedHashMap[String, Any] = null

  var request: Request = null

  var skip: Boolean = false





}
