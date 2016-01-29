package com.spider.downloader

import com.spider.model.{Site, Task}
import com.spider.model.downloader.{Page, Request}

/**
  * Created by jason on 16-1-28.
  */
class HttpClientDownloder extends Downloader{

  override def download(request: Request, task: Task): Page = {


    new Page
  }
}
