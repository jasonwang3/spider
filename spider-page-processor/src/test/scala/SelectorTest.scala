import com.spider.model.processor.OAContent
import com.spider.processor.selector.Selectable
import com.spider.processor.selector.impl.{Html, HtmlNode}
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.io.Source
import scala.util.matching.Regex

/**
  * Created by jason on 16-4-13.
  */
class SelectorTest extends FlatSpec with Matchers with BeforeAndAfter {

  "parse OA" should "correctly" in {
    val source = Source.fromURL(getClass.getResource("data/oa.html")).mkString
    val html = Html(source)
    val list = html.css("div.bDiv").links.regex(".*/seeyon/collaboration.do?.*affairId=.*").all
    list.size should be(20)
  }


  "parse content page" should "get needed content" in {
    val source = Source.fromURL(getClass.getResource("data/content.html")).mkString
    val html: Html = Html(source)
    val list = html.css("table.body-detail-form").xpath("script")
    val content: String = list.get
    var OAContent: OAContent = new OAContent
    var market = getContent(content, "促销方案")
    println(market)
  }

  def getContent(content: String, keyWord: String) = {
    val regex: Regex = new Regex("<my:"+ keyWord +".*>.*</my:" + keyWord +">")
    val regexFont: Regex = new Regex("<my:"+ keyWord +".*?>")
    val regexEnd: Regex = new Regex("</my:" + keyWord +">")
    var realContent = regex.findFirstMatchIn(content).get.toString()
    val font = regexFont.findFirstMatchIn(realContent).get.source.toString
    val end = regexEnd.findFirstMatchIn(realContent).get.source.toString
    realContent = realContent.replace(font, "").replace(end, "")
    realContent
  }

}
