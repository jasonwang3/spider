package com.spider.model.downloader

import com.spider.model.Site

/**
  * Created by jason on 16-4-11.
  */
class DownloadRequest(_request: Request, _site: Site) {
  val request: Request = _request
  val site: Site = _site
}
