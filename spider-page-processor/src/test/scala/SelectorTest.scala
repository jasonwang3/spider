import com.spider.processor.selector.impl.Html
import com.spider.processor.util.UrlUtils
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.io.Source
/**
  * Created by jason on 16-4-13.
  */
class SelectorTest extends FlatSpec with Matchers with BeforeAndAfter{


  "parse OA" should "correctly" in {
    val source = Source.fromURL(getClass.getResource("data/oa.html")).mkString
    val html = Html(UrlUtils.fixAllRelativeHrefs(source, "http://219.238.188.179"))
    val list = html.css("div.bDiv").links.regex(".*/seeyon/collaboration.do?method=detail&from=Pending&affairId=.*").all
    print(list)
  }
}
