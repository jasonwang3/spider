import java.util.regex.{Matcher, Pattern}

import com.spider.processor.selector.impl.Html
import org.apache.commons.lang3.StringUtils
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.io.Source
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

  "parse test" should "correctly" in {
    var regexStr = Source.fromURL(getClass.getResource("data/test.txt")).mkString
    if (StringUtils.countMatches(regexStr, "(") - StringUtils.countMatches(regexStr, "\\(") == StringUtils.countMatches(regexStr, "(?:") - StringUtils.countMatches(regexStr, "\\(?:")) {
      regexStr = "(" + regexStr + ")"
    }
    val pattern = Pattern.compile("(?<=<my:专柜名称.{0,100}>).*?(?=</my:专柜名称>)", Pattern.DOTALL | Pattern.CASE_INSENSITIVE)
    val matcher: Matcher = pattern.matcher(regexStr)
    while (matcher.find) {
      val groups: Array[String] = new Array[String](matcher.groupCount + 1)
      groups(0) = matcher.group(0)
      groups.foreach(println _)
    }
  }
}
