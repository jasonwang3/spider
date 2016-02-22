package downloader

import com.spider.downloader.HttpClientDownloader
import com.spider.model.Site
import com.spider.model.downloader.Request
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

import scala.collection.immutable.Stack


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
    httpClientDownloader.download(new Request("https://github.com"),Site.create().toTask())
  }

}
