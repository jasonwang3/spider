package com.spider.model

import com.spider.model.Action.Action
import com.spider.model.downloader.{DownloadRequest, Request}

import scala.collection.mutable

/**
  * Created by jason on 16-4-19.
  */
class Spider(_id: String, _request: Request, _site: Site, _actions: mutable.HashMap[Rule, Action]) {

  var id: String = _id

  val request: Request = _request

  val site: Site = _site

  val actions: mutable.HashMap[Rule, Action] = _actions


  override def toString = s"Spider(id=$id, request=$request, site=$site, actions=$actions)"
}
