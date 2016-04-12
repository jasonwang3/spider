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

class HttpClientDownloaderTest extends FlatSpec with Matchers with BeforeAndAfter {
  var applicationContext: ApplicationContext = null
  var httpClientDownloader: HttpClientDownloader = null

  before {
    applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml")
    httpClientDownloader = applicationContext.getBean("httpClientDownloader").asInstanceOf[HttpClientDownloader]
  }


  "download" should "download a page" in {
    val html = httpClientDownloader.download("https://github.com")
    html.getFirstSourceText.isEmpty should be(false)
  }

  "download with cookies" should "download a page" in {
    val site: Site = Site.create().setDomain(".github.com").addCookie("_ga", "GA1.2.328404385.1436499079")
      .addCookie(".github.com", "dotcom_user", "jasonwang3")
      .addCookie(".github.com", "logged_in", "yes")
      .addCookie("github.com", "tz", "Asia%2FShanghai")
      .addCookie("github.com", "user_session", "nW84V1XqEMGOU0ZwCFw19lAULBCSdNMDXztlcpdIESwGqCbOVwYXyadqNNa9vG6snuxlzNjmigc9WFHw")
    val page = httpClientDownloader.download(new Request("https://github.com"), site.toTask())
    page.getHtml.getFirstSourceText.isEmpty should be(false)
  }

  "download http://219.238.188.179/" should "download a page" in {
    val site: Site = Site.create().setDomain("219.238.188.179").addCookie("219.238.188.179", "JSESSIONID", "D9656D8AC0FD9151C0666D5CDEBB94C1")
      .addCookie("219.238.188.179", "login.locale", "zh_CN")
      .addHeader("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")
    val page = httpClientDownloader.download(new Request("http://219.238.188.179/seeyon/main.do?main.do?app=Coll&method=morePending4App&_spage=&page=2&count=125&pageSize=20"), site.toTask())
    println(page.getHtml.getDocument)
  }

}
