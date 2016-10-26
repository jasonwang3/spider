package com.spider.downloader

import com.spider.model.Site
import com.spider.model.downloader.{Page, Request}

/**
  * Created by jason on 16-2-2.
  */
abstract class AbstractDownloader extends Downloader{

  def download(url: String): Page = {
    download(url, null)
  }

  def download(url: String, charset: String): Page = {
    val site = new Site
    site.charset = charset
    download(new Request(url), site.toTask)
  }
}
