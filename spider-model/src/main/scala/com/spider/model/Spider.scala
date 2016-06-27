package com.spider.model

import com.spider.model.Action.Action
import com.spider.model.downloader.{DownloadRequest, Request}

import scala.collection.mutable

/**
  * Created by jason on 16-4-19.
  */
class Spider(_id: String, _request: Request, _site: Site, _rules: List[Rule]) {

  var id: String = _id

  val request: Request = _request

  val site: Site = _site

  val rules: List[Rule] = _rules


  override def toString = s"Spider(id=$id, request=$request, site=$site, rules=$rules)"
}
