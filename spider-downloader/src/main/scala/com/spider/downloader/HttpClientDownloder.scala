package com.spider.downloader

import com.spider.model.{Site, Task}
import com.spider.model.downloader.{Page, Request}
import org.apache.http.client.methods.{HttpUriRequest, CloseableHttpResponse}

/**
  * Created by jason on 16-1-28.
  */
class HttpClientDownloder extends Downloader{

  override def download(request: Request, task: Task): Page = {
    var site: Site = null
    if (task != null) {
      site = task.site
    }
    var charset: String = null
    var header: Map[String, String] = null
    var acceptStatCode = Set(200)
    if (site != null) {
      acceptStatCode = site.acceptStatCode
      charset = site.charset
      header = site.headers
    }
    var httpResponse: CloseableHttpResponse = null


    new Page
  }

//  protected def getHttpUriRequest(request: Request, site: Site, headers: Map[String, String]): HttpUriRequest {
//
//  }


}
