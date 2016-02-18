package com.spider.downloader

import com.spider.model.downloader.{Page, Request}
import com.spider.model.utils.HttpConstant
import com.spider.model.{Site, Task}
import org.apache.http.NameValuePair
import org.apache.http.client.config.{CookieSpecs, RequestConfig}
import org.apache.http.client.methods.{CloseableHttpResponse, HttpUriRequest, RequestBuilder}

/**
  * Created by jason on 16-1-28.
  */
class HttpClientDownloader extends Downloader {

  override def download(request: Request, task: Task): Page = {
    var site: Site = null
    if (task != null) {
      site = task.site
    }
    var charset: String = null
    var headers: Map[String, String] = null
    var acceptStatCode = Set(200)
    if (site != null) {
      acceptStatCode = site.acceptStatCode
      charset = site.charset
      headers = site.headers
    }
    var httpResponse: CloseableHttpResponse = null
    var statusCode: Int = 0

    val httpUriRequest: HttpUriRequest = getHttpUriRequest(request, site, headers)
//    httpResponse = getHttpClient(site).execute(httpUriRequest)

    new Page
  }

  protected def getHttpUriRequest(request: Request, site: Site, headers: Map[String, String]): HttpUriRequest = {
    val requestBuilder: RequestBuilder = selectRequestMethod(request).setUri(request.url)
    if (headers != null) {
      headers.foreach(headers => requestBuilder.addHeader(headers._1, headers._2))
    }
    val requestConfigBuilder: RequestConfig.Builder = RequestConfig.custom().setConnectionRequestTimeout(site.timeOut)
      .setSocketTimeout(site.timeOut).setConnectTimeout(site.timeOut).setCookieSpec(CookieSpecs.DEFAULT);
    //TODO:support for proxy
    requestBuilder.setConfig(requestConfigBuilder.build)
    requestBuilder.build()
  }

  protected def selectRequestMethod(request: Request): RequestBuilder = {
    val method = request.method
    if (method == null || method.equalsIgnoreCase(HttpConstant.Method.GET)) {
      RequestBuilder.get()
    } else if (method.equalsIgnoreCase(HttpConstant.Method.POST)) {
      val requestBuilder: RequestBuilder = RequestBuilder.post
      val nameValuePair: Array[NameValuePair] = request.getExtra("nameValuePair").asInstanceOf[Array[NameValuePair]]
      if (nameValuePair != null && nameValuePair.length > 0) {
        nameValuePair.foreach(value => requestBuilder.addParameter(value))
      }
    } else if (method.equalsIgnoreCase(HttpConstant.Method.HEAD)) {
      return RequestBuilder.head
    }
    else if (method.equalsIgnoreCase(HttpConstant.Method.PUT)) {
      return RequestBuilder.put
    }
    else if (method.equalsIgnoreCase(HttpConstant.Method.DELETE)) {
      return RequestBuilder.delete
    }
    else if (method.equalsIgnoreCase(HttpConstant.Method.TRACE)) {
      return RequestBuilder.trace
    }
    throw new IllegalArgumentException("Illegal HTTP Method " + method)

  }

//  def getHttpClient(site: Site): CloseableHttpResponse = {
//    if (site == null) {
//
//    }
//  }


}
