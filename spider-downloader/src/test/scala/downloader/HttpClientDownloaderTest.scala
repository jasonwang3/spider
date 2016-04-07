package downloader

import com.spider.downloader.HttpClientDownloader
import com.spider.model.Site
import com.spider.model.downloader.Request
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext


/**
  * Created by wxy on 16/2/21.
  */

class HttpClientDownloaderTest extends FlatSpec with Matchers with BeforeAndAfter{
  var applicationContext:ApplicationContext = null
  var httpClientDownloader: HttpClientDownloader = null

  before {
    applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml")
    httpClientDownloader = applicationContext.getBean("httpClientDownloader").asInstanceOf[HttpClientDownloader]
  }


  "download" should "download a page" in {
    val html = httpClientDownloader.download("https://github.com")
    html.getFirstSourceText.isEmpty should be (false)
  }

  "download with cookies" should "download a page" in {
    var site: Site = Site.create().setDomain(".github.com").addCookie("_ga", "GA1.2.328404385.1436499079")
      .addCookie(".github.com", "_gat", "1")
      .addCookie("github.com", "_gh_sess", "eyJzZXNzaW9uX2lkIjoiNDY0YWE3NjFlODdjZDEwNmI1ZTc3MzU5Zjc5NmNlZTgiLCJjb250ZXh0IjoiLyIsInNweV9yZXBvIjoiRXZlcmJyaWRnZS9vbW5pcGxhdGZvcm0iLCJzcHlfcmVwb19hdCI6MTQ2MDAwNjk3MSwibGFzdF93cml0ZSI6MTQ2MDAwNjkxNTYzMSwiZmxhc2giOnsiZGlzY2FyZCI6WyJhbmFseXRpY3NfbG9jYXRpb24iXSwiZmxhc2hlcyI6eyJhbmFseXRpY3NfbG9jYXRpb24iOiIvZGFzaGJvYXJkIn19fQ")
      .addCookie(".github.com", "_octo", "GH1.1.635275185.1436499080")
      .addCookie(".github.com", "dotcom_user", "jasonwang3")
      .addCookie(".github.com", "logged_in", "yes")
      .addCookie("github.com", "tz", "Asia%2FShanghai")
      .addCookie("github.com", "user_session", "nW84V1XqEMGOU0ZwCFw19lAULBCSdNMDXztlcpdIESwGqCbOVwYXyadqNNa9vG6snuxlzNjmigc9WFHw")
    val page = httpClientDownloader.download(new Request("https://github.com"), site.toTask())
    page.getHtml.getFirstSourceText.isEmpty should be (false)
  }

}
