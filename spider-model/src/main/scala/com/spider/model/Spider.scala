package com.spider.model

import java.util.Date

import com.spider.model.downloader.Request

/**
  * Created by jason on 16-4-19.
  */
@SerialVersionUID(1L)
class Spider(_id: String, _request: Request, _site: Site, _rules: List[Rule]) {

  var id: String = _id

  val request: Request = _request

  val site: Site = _site

  val rules: List[Rule] = _rules

  var startDate: Date = null


  override def toString = s"Spider(id=$id, request=$request, site=$site, rules=$rules)"
}
