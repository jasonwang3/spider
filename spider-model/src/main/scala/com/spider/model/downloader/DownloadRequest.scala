package com.spider.model.downloader

import com.spider.model.Site

/**
  * Created by jason on 16-4-11.
  */
class DownloadRequest(_request: Request, _site: Site, _spiderId: String, _step: Int) extends Serializable {

  val spiderId: String = _spiderId

  val step: Int = _step

  val request: Request = _request

  val site: Site = _site

}
