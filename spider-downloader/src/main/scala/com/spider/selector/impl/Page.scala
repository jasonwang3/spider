package com.spider.selector.impl

import com.spider.model.downloader.{Request, ResultItems}
import com.spider.selector.Selectable

/**
  * Created by jason on 16-1-22.
  */
class Page {

  var request: Request = null

  var resultItems: ResultItems = new ResultItems

  var rawText: String = null

  var url: Selectable = null

  var statusCode: Int = 0

}
