package com.spider.model.downloader

/**
  * Created by jason on 16-1-22.
  */
class Page {

  var request: Request = null

  var resultItems: ResultItems = new ResultItems

  var rawText: String = null

//  var url: Selectable = null

//  private var html: Html = null

  var statusCode: Int = 0

//  def getHtml: Html = {
//    if (html == null) {
//      html = Html(UrlUtils.fixAllRelativeHrefs(rawText, request.url))
//    }
//    return html
//  }
}
