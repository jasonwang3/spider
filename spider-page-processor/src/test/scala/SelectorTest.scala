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

  "parse OA" should "corectly" in {
    val source = Source.fromURL(getClass.getResource("data/oa.html")).mkString
    val html = Html(source)
    val list = html.css("div.bDiv").links.regex("/seeyon/collaboration\\.do\\?.*affairId=.*Pending").all
    println(list)
  }




  "parse OA" should "correctly" in {
    val source = Source.fromURL(getClass.getResource("data/oa.html")).mkString
    val html = Html(source)
    val list = html.css("div.bDiv").links.regex(".*/seeyon/collaboration.do?.*affairId=.*").all
    val path = "D:/url.txt"
    val file = new File(path)
    file.createNewFile()
    val out = new FileOutputStream(file, false)
    var sb = new StringBuffer()
    list.foreach(url => {
      sb.append(url + "\r\n")
    })
    out.write(sb.toString().getBytes("utf-8"))
    out.close()
    //    list.size should be(20)
  }

  "parse OA" should "correctly2" in {
    val folder = new File("D:/htmls")
    var sb = new StringBuffer()
    val files = folder.listFiles()
    files.foreach(file => {
      var sb2 = new StringBuffer()
      var reader: BufferedReader = null
      reader = new BufferedReader(new FileReader(file))
      var tempString: String = null
      var line = 1
      breakable {
        while ((tempString = reader.readLine()) != null) {
          if (tempString != null) {
            sb2.append(tempString)
            line += 1
          } else {
            break
          }

        }
      }
      reader.close()
      val html = Html(sb2.toString)
      val list = html.xpath("//frame[@id='detailMainFrame']/@src").all
      if (list(0) != null) {
        sb.append(list(0) + "\r\n")
        writeFile(sb.toString, "D:/realUrl.txt")
      }

    })


  }

  def writeFile(str: String, file: String): Unit = {
    val out = new FileOutputStream(file, false)
    var sb = new StringBuffer()
    out.write(str.getBytes("utf-8"))
    out.close()
  }


  "parse content page" should "get needed content" in {
    val source = Source.fromURL(getClass.getResource("data/content.html")).mkString
    var lists:util.ArrayList[OAContent] = new util.ArrayList()
    for(n <- 1 to 318) {
      val file = new File("D:/realHtmls/" + n + ".html")
      println(file.getName)
      var sb2 = new StringBuffer()
      var reader: BufferedReader = null
      reader = new BufferedReader(new FileReader(file))
      var tempString: String = null
      var line = 1
      breakable {
        while ((tempString = reader.readLine()) != null) {
          if (tempString != null) {
            sb2.append(tempString)
            line += 1
          } else {
            break
          }

        }
      }
      reader.close()
      val html = Html(sb2.toString)
      val list = html.css("table.body-detail-form").xpath("script")
      val content: String = list.get
      var OAContent: OAContent = new OAContent
      try {
        OAContent.market = getContent(content, "所属市场")
        OAContent.office = getContent(content, "分公司新")
        OAContent.applyDate = getContent(content, "申请日期")
        OAContent.specialName = getContent(content, "专柜名称")
        OAContent.promotionsType = getContent(content, "促销类别")
        OAContent.beginDate = getContent(content, "促销期间从")
        OAContent.endDate = getContent(content, "促销期间到")
        OAContent.plan = getContent(content, "促销方案")
        OAContent.situation = getContent(content, "竞品促销活动情况")
        lists.add(OAContent)
      } catch {
        case e: Exception => {
          lists.add(OAContent)
        }
      }
    }
    val exportExcel: ExportExcel = new ExportExcel
    exportExcel.exportExcel(lists.toList)

  }

  def getContent(content: String, keyWord: String) = {
    val regex: Regex = new Regex("<my:" + keyWord + ".*>.*</my:" + keyWord + ">")
    val regexFont: Regex = new Regex("<my:" + keyWord + ".*?>")
    val regexEnd: Regex = new Regex("</my:" + keyWord + ">")
    var realContent = regex.findFirstMatchIn(content).get.toString
    val font = regexFont.findFirstMatchIn(realContent).get.toString
    val end = regexEnd.findFirstMatchIn(realContent).get.toString
    realContent = realContent.replace(font, "").replace(end, "")
    realContent
  }


}
