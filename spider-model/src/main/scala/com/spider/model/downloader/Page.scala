package com.spider.model.downloader

/**
  * Created by jason on 16-1-22.
  */
@SerialVersionUID(1L)
class Page extends Serializable{

  var request: Request = null

  var resultItems: ResultItems = new ResultItems

  var rawText: String = null

  var statusCode: Int = 0

  var step: Int = 0

  override def toString = s"Page(request=$request, resultItems=$resultItems, rawText=$rawText, statusCode=$statusCode, step=$step)"
}
