package com.spider.downloader

import com.spider.model.Task
import com.spider.model.downloader.Request
import com.spider.selector.impl.Page

/**
  * Created by jason on 16-1-28.
  */
trait Downloader {

  def download(request: Request, task: Task): Page


}
