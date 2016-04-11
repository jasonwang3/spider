package com.spider.downloader

import com.spider.model.Site
import com.spider.model.downloader.Request
import com.spider.selector.impl.{Html, Page}

/**
  * Created by jason on 16-2-2.
  */
abstract class AbstractDownloader extends Downloader{

  def download(url: String): Html = {
    download(url, null)
  }

  def download(url: String, charset: String): Html = {
    val page: Page = download(new Request(url), Site.create().setCharset(charset).toTask)
    page.getHtml
  }
}
