package com.spider.model

import com.spider.model.downloader.DownloadRequest
import scala.collection.mutable

/**
  * Created by jason on 16-4-19.
  */
class Spider {

  var id: String = null

  var downloadRequest: DownloadRequest = null

  var actions: mutable.HashMap[Rule, Action.Value] = null
}
