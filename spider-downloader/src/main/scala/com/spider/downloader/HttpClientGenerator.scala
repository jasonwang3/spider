package com.spider.downloader

import java.io.IOException

import com.spider.model.Site
import org.apache.http.client.CookieStore
import org.apache.http.impl.cookie.BasicClientCookie
import org.apache.http.protocol.HttpContext
import org.apache.http.{HttpRequest, HttpRequestInterceptor, HttpException}
import org.apache.http.config.{SocketConfig, Registry, RegistryBuilder}
import org.apache.http.conn.socket.{PlainConnectionSocketFactory, ConnectionSocketFactory}
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.impl.client._
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager

/**
  * Created by jason on 2016/2/18.
  */

object HttpClientGenerator {
  def apply(): HttpClientGenerator = {
    val reg: Registry[ConnectionSocketFactory] = RegistryBuilder.create[ConnectionSocketFactory]
      .register("http", PlainConnectionSocketFactory.INSTANCE)
      .register("https", SSLConnectionSocketFactory.getSocketFactory)
      .build
    val generator: HttpClientGenerator = new HttpClientGenerator
    generator.connectionManager = new PoolingHttpClientConnectionManager(reg)
    generator.connectionManager.setDefaultMaxPerRoute(100)
    generator
  }
}

class HttpClientGenerator private(){
  var connectionManager: PoolingHttpClientConnectionManager = null

//  def getClient(site: Site): CloseableHttpClient = {
//
//  }

  private def generateClient(site: Site): CloseableHttpClient = {
    val httpClientBuilder: HttpClientBuilder = HttpClients.custom.setConnectionManager(connectionManager)
    if (site != null && site.userAgent != null ) {
      httpClientBuilder.setUserAgent(site.userAgent)
    } else {
      httpClientBuilder.setUserAgent("")
    }
    if (site != null || site.useGzip) {
      httpClientBuilder.addInterceptorFirst(new HttpRequestInterceptor() {
        @throws(classOf[HttpException])
        @throws(classOf[IOException])
        def process(request: HttpRequest, context: HttpContext) {
          if (!request.containsHeader("Accept-Encoding")) {
            request.addHeader("Accept-Encoding", "gzip")
          }
        }
      })
    }
    val socketConfig: SocketConfig = SocketConfig.custom.setSoKeepAlive(true).setTcpNoDelay(true).build
    httpClientBuilder.setDefaultSocketConfig(socketConfig)
    if (site != null) {
      httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(site.retryTimes, true))
    }
    generateCookie(httpClientBuilder, site)



    httpClientBuilder.build()
  }

  def generateCookie(httpClientBuilder: HttpClientBuilder, site: Site) = {
    val cookieStore: CookieStore = new BasicCookieStore
    site.defaultCookies.foreach(cookies => {
      val cookie: BasicClientCookie = new BasicClientCookie(cookies._1, cookies._2)
      cookie.setDomain(site.domain)
      cookieStore.addCookie(cookie)
    })

  }

}





