import java.io._
import java.util

import com.spider.model.processor.OAContent
import com.spider.processor.selector.Selectable
import com.spider.processor.selector.impl.{Html, HtmlNode}
import com.spider.processor.util.ExportExcel
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import scala.util.control.Breaks._
import scala.io.Source
import scala.util.matching.Regex
import scala.collection.JavaConversions._
/**
  * Created by jason on 16-4-13.
  */
class SelectorTest extends FlatSpec with Matchers with BeforeAndAfter {

  "parse OA" should "correctly" in {
    val source = Source.fromURL(getClass.getResource("data/test.html")).mkString
    val html = Html(source)
    val list = html.xpath("//iframe[@id='contentIframe']/@src")
    println(list)
  }

  "parse JD" should "correctly" in {
    val source = Source.fromURL(getClass.getResource("data/123.html")).mkString
    val html = Html(source)
    val html2 = html.css("div[data-sku]").all
    print(html2)
  }
}
