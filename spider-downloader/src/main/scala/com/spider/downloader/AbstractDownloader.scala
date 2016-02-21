package com.spider.downloader

import com.spider.model.Task
import com.spider.model.downloader.{Page, Request}

/**
  * Created by jason on 16-2-2.
  */
abstract class AbstractDownloader extends Downloader{


  def download(url: String): Html = {

  }

}
