package com.spider.downloader

import java.io.IOException
import java.nio.charset.Charset

import com.spider.downloader.util.UrlUtils
import com.spider.model.downloader.Request
import com.spider.model.utils.HttpConstant
import com.spider.model.{Site, Task}
import com.spider.selector.impl.{Page, PlainText}
import org.apache.commons.io.IOUtils
import org.apache.http.client.config.{CookieSpecs, RequestConfig}
import org.apache.http.client.methods.{CloseableHttpResponse, HttpUriRequest, RequestBuilder}
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.util.EntityUtils
import org.apache.http.{HttpResponse, NameValuePair}
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.slf4j.LoggerFactory

import scala.beans.BeanProperty
import scala.collection.JavaConversions._
import scala.collection.immutable.HashMap

/**
  * Created by jason on 16-1-28.
  */


object HttpClientDownloader {
  val logger = LoggerFactory.getLogger(classOf[HttpClientDownloader])
}

class HttpClientDownloader extends AbstractDownloader {
  @BeanProperty
  var httpClientGenerator: HttpClientGenerator = null
  var httpClients: HashMap[String, CloseableHttpClient] = HashMap[String, CloseableHttpClient]()

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
    try {
      val httpUriRequest: HttpUriRequest = getHttpUriRequest(request, site, headers)
      httpResponse = getHttpClient(site).execute(httpUriRequest)
      statusCode = httpResponse.getStatusLine.getStatusCode
      request.putExtra(Request.STATUS_CODE, statusCode)
      if (statusAccept(acceptStatCode, statusCode)) {
        var page = handleResponse(request, charset, httpResponse, task)
        return page
      }
    } catch {
      case e: IOException => {
        HttpClientDownloader.logger.warn("download page " + request.url + " error", e)
        return null
      }
    } finally {
      request.putExtra(Request.STATUS_CODE, statusCode)
      try {
        if (httpResponse != null) {
          EntityUtils.consume(httpResponse.getEntity)
        }
      }
      catch {
        case e: IOException => {
          HttpClientDownloader.logger.warn("close response fail", e)
        }
      }
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
      return RequestBuilder.get()
    } else if (method.equalsIgnoreCase(HttpConstant.Method.POST)) {
      val requestBuilder: RequestBuilder = RequestBuilder.post
      val nameValuePair: Array[NameValuePair] = request.getExtra("nameValuePair").asInstanceOf[Array[NameValuePair]]
      if (nameValuePair != null && nameValuePair.length > 0) {
        nameValuePair.foreach(value => requestBuilder.addParameter(value))
      }
      return requestBuilder
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
    var httpClient: CloseableHttpClient = httpClients.getOrElse(site.domain, null)
    if (httpClient == null) {
      this synchronized {
        httpClient = httpClients.getOrElse(site.domain, null)
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


  protected def handleResponse(request: Request, charset: String, httpResponse: HttpResponse, task: Task): Page = {
    val content: String = getContent(charset, httpResponse)
    val page: Page = new Page
    page.rawText = content
    page.url = new PlainText(request.url)
    page.request = request
    page.statusCode = httpResponse.getStatusLine.getStatusCode
    page
  }

  @throws(classOf[IOException])
  protected def getContent(charset: String, httpResponse: HttpResponse): String = {
    if (charset == null) {
      val contentBytes: Array[Byte] = IOUtils.toByteArray(httpResponse.getEntity.getContent)
      val htmlCharset: String = getHtmlCharset(httpResponse, contentBytes)
      if (htmlCharset != null) {
        return new String(contentBytes, htmlCharset)
      }
      else {
        HttpClientDownloader.logger.warn("Charset autodetect failed, use {} as charset. Please specify charset in Site.setCharset()", Charset.defaultCharset)
        return new String(contentBytes)
      }
    }
    else {
      return IOUtils.toString(httpResponse.getEntity.getContent, charset)
    }
  }

  @throws(classOf[IOException])
  protected def getHtmlCharset(httpResponse: HttpResponse, contentBytes: Array[Byte]): String = {
    var charset: String = null
    val value: String = httpResponse.getEntity.getContentType.getValue
    charset = UrlUtils.getCharset(value)
    if (charset.isEmpty) {
      HttpClientDownloader.logger.debug("Auto get charset: {}", charset)
      return charset
    }
    val defaultCharset: Charset = Charset.defaultCharset
    val content: String = new String(contentBytes, defaultCharset.name)
    if (!content.isEmpty) {
      val document: Document = Jsoup.parse(content)
      val links: Elements = document.select("meta")
      links.find(element => {
        var metaContent: String = element.attr("content")
        var metaCharset: String = element.attr("charset")
        if (metaContent.indexOf("charset") != -1) {
          metaContent = metaContent.substring(metaContent.indexOf("charset"), metaContent.length)
          charset = metaContent.split("=")(1)
          true
        } else if (!metaCharset.isEmpty) {
          charset = metaCharset
          true
        } else {
          false
        }
      })

    }
    HttpClientDownloader.logger.debug("Auto get charset: {}", charset)
    charset
  }


}
