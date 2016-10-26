package com.spider.model

import java.util.Date

import com.spider.model.downloader.Request

/**
  * Created by jason on 16-4-19.
  */
@SerialVersionUID(1L)
class Spider(val request: Request, val site: Site, val rules: List[Rule]) {

  var id: String = null

  var startDate: Date = null

  override def toString = s"Spider(id=$id, request=$request, site=$site, rules=$rules)"
}
