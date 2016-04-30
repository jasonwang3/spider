package downloader

import java.io._

import com.spider.downloader.HttpClientDownloader
import com.spider.model.Site
import com.spider.model.downloader.Request
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import scala.util.control.Breaks._

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
    val page = httpClientDownloader.download("https://github.com")
    page.rawText should not be (null)
  }

  "download with cookies" should "download a page" in {
    val site: Site = Site.create().setDomain(".github.com").addCookie("_ga", "GA1.2.328404385.1436499079")
      .addCookie(".github.com", "dotcom_user", "jasonwang3")
      .addCookie(".github.com", "logged_in", "yes")
      .addCookie("github.com", "tz", "Asia%2FShanghai")
      .addCookie("github.com", "user_session", "nW84V1XqEMGOU0ZwCFw19lAULBCSdNMDXztlcpdIESwGqCbOVwYXyadqNNa9vG6snuxlzNjmigc9WFHw")
    val page = httpClientDownloader.download(new Request("https://github.com"), site.toTask())
  }

  "download http://219.238.188.179/" should "download a page2" in {
    val site: Site = Site.create().setDomain("219.238.188.179").addCookie("219.238.188.179", "JSESSIONID", "1EB83B0235F4A00B0F82DFE1B926C88E")
      .addCookie("219.238.188.179", "login.locale", "zh_CN")
      .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")
    val path = "D:/url.txt"
    val file = new File(path)
    val fis = new FileInputStream(file)
    val br = new BufferedReader(new InputStreamReader(fis))
    var tempstr: String = null
    var count = 0
    breakable {
      while ((tempstr = br.readLine()) != null) {
        if (tempstr != null) {
          count += 1
          val page = httpClientDownloader.download(new Request(tempstr), site.toTask())
          val path = "D:/htmls/" + count + ".html"
          val file = new File(path)
          val out = new FileOutputStream(file, false)
          var sb = new StringBuffer()
          out.write(page.rawText.getBytes("utf-8"))
          out.close()
        } else {
          break
        }

      }
    }
    //    val page = httpClientDownloader.download(new Request("http://219.238.188.179/seeyon/collaboration.do?method=getContent&summaryId=3524463934222780404&affairId=1414068954692864924&from=Pending&isQuote=&type=&lenPotent="), site.toTask())
    //    page.rawText should not be (null)
  }

  "download http://219.238.188.179/" should "download a page3" in {
    val site: Site = Site.create().setDomain("219.238.188.179").addCookie("219.238.188.179", "JSESSIONID", "1EB83B0235F4A00B0F82DFE1B926C88E")
      .addCookie("219.238.188.179", "login.locale", "zh_CN")
      .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")
    val path = "D:/realUrl.txt"
    val file = new File(path)
    val fis = new FileInputStream(file)
    val br = new BufferedReader(new InputStreamReader(fis))
    var tempstr: String = null
    var count = 0
    breakable {
      while ((tempstr = br.readLine()) != null) {
        if (tempstr != null) {
          count += 1
          val page = httpClientDownloader.download(new Request(tempstr), site.toTask())
          val path = "D:/realHtmls/" + count + ".html"
          val file = new File(path)
          val out = new FileOutputStream(file, false)
          var sb = new StringBuffer()
          out.write(page.rawText.getBytes("utf-8"))
          out.close()
        } else {
          break
        }

      }
    }
    //    val page = httpClientDownloader.download(new Request("http://219.238.188.179/seeyon/collaboration.do?method=getContent&summaryId=3524463934222780404&affairId=1414068954692864924&from=Pending&isQuote=&type=&lenPotent="), site.toTask())
    //    page.rawText should not be (null)
  }


  "download http://219.238.188.179/" should "download a page1" in {
    val site: Site = Site.create().setDomain("219.238.188.179").addCookie("219.238.188.179", "JSESSIONID", "1EB83B0235F4A00B0F82DFE1B926C88E")
      .addCookie("219.238.188.179", "login.locale", "zh_CN")
      .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")
    val page = httpClientDownloader.download(new Request("http://219.238.188.179/seeyon/main.do?app=Coll&method=morePending4App&_spage=&page=1&pageSize=400"), site.toTask())
    page.rawText should not be (null)
  }


  "download http://219.238.188.179/" should "download a page12" in {
    val site: Site = Site.create().setDomain("219.238.188.179").addCookie("219.238.188.179", "JSESSIONID", "BC73FD8D560469D618A62FB63CB7BF2E")
      .addCookie("219.238.188.179", "login.locale", "zh_CN")
      .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")
    val page = httpClientDownloader.download(new Request("http://219.238.188.179/seeyon/collaboration.do?method=summary&summaryId=-6715989472523697684&affairId=-3829689147039988207&from=Pending&isQuote=&type=&lenPotent=&contentAnchor="), site.toTask())
    page.rawText should not be (null)
  }

}
