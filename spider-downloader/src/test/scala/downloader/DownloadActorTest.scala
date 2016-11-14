package downloader

import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

/**
  * Created by jason on 16-11-14.
  */
class DownloadActorTest extends FlatSpec with Matchers with BeforeAndAfter {
  "download" should "download a page" in {
    val url = "http://img.alicdn.com/bao/uploaded/i2/TB1bM7tNFXXXXc3XpXXXXXXXXXX_!!0-item_pic.jpg_b.jpg"
    val closeableHttpClient: CloseableHttpClient = HttpClients.createDefault()
    val httpGet = new HttpGet(url)
    httpGet.setHeader("User-Agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
    val response: HttpResponse = closeableHttpClient.execute(httpGet)

  }
}
