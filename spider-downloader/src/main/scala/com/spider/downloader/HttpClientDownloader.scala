package com.spider.downloader

import java.io.IOException
import java.nio.charset.Charset

import com.spider.model.downloader.{Page, Request}
import com.spider.model.utils.HttpConstant
import com.spider.model.{Site, Task}
import org.apache.http.{HttpResponse, NameValuePair}
import org.apache.http.client.config.{CookieSpecs, RequestConfig}
import org.apache.http.client.methods.{CloseableHttpResponse, HttpUriRequest, RequestBuilder}
import org.apache.http.impl.client.CloseableHttpClient

import scala.beans.BeanProperty
import scala.collection.immutable.HashMap

/**
  * Created by jason on 16-1-28.
  */
class HttpClientDownloader extends AbstractDownloader {
  @BeanProperty
  var httpClientGenerator: HttpClientGenerator = null
  var httpClients: HashMap[String, CloseableHttpClient] = new HashMap[String, CloseableHttpClient]()

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
    httpResponse = getHttpClient(site).execute(httpUriRequest)
    statusCode = httpResponse.getStatusLine.getStatusCode
    request.putExtra(Request.STATUS_CODE, statusCode)
    if (statusAccept(acceptStatCode, statusCode)) {

    }

    new Page
  }

  protected def getHttpUriRequest(request: Request, site: Site, headers: Map[String, String]): HttpUriRequest = {
    val requestBuilder: RequestBuilder = selectRequestMethod(request).setUri(request.url)
    if (headers != null) {
      headers.foreach(headers => requestBuilder.addHeader(headers._1, headers._2))
    }
    val requestConfigBuilder: RequestConfig.Builder = RequestConfig.custom().setConnectionRequestTimeout(site.timeOut)
      .setSocketTimeout(site.timeOut).setConnectTimeout(site.timeOut).setCookieSpec(CookieSpecs.DEFAULT)
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

  def getHttpClient(site: Site): CloseableHttpClient = {
    if (site == null) {
      return httpClientGenerator.getClient(null)
    }
    var httpClient: CloseableHttpClient = httpClients.get(site.domain).get
    if (httpClient == null) {
      this synchronized {
        httpClient = httpClients.get(site.domain).get
        if (httpClient == null) {
          httpClient = httpClientGenerator.getClient(site)
          httpClients += (site.domain -> httpClient)
        }
      }
    }
    httpClient
  }

  protected def statusAccept(acceptStatCode: Set[Int], statusCode: Int): Boolean = {
    acceptStatCode.contains(statusCode)
  }


//  protected def handleResponse(request: Request, charset: String, httpResponse: HttpResponse, task: Task): Page = {
//
//  }

  @throws(classOf[IOException])
  protected def getContent(charset: String, httpResponse: HttpResponse): String = {
    if (charset == null) {
      val contentBytes: Array[Byte] = IOUtils.toByteArray(httpResponse.getEntity.getContent)
      val htmlCharset: String = getHtmlCharset(httpResponse, contentBytes)
      if (htmlCharset != null) {
        return new String(contentBytes, htmlCharset)
      }
      else {
        logger.warn("Charset autodetect failed, use {} as charset. Please specify charset in Site.setCharset()", Charset.defaultCharset)
        return new String(contentBytes)
      }
    }
    else {
      return IOUtils.toString(httpResponse.getEntity.getContent, charset)
    }
    "123"
  }
}
