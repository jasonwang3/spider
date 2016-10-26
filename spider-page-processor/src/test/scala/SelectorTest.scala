import java.util.regex.{Matcher, Pattern}

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import com.spider.model.Action.{apply => _, _}
import com.spider.model.processor.{AnalyzeRequest, AnalyzeResponse}
import com.spider.model.selector.ContentSelector
import com.spider.model.support.SelectorType._
import com.spider.model.{MatchRule, Rule}
import com.spider.processor.actor.ProcessorActor
import com.typesafe.config.ConfigFactory
import org.apache.commons.lang3.StringUtils
import org.scalatest._

import scala.concurrent.duration._
import scala.io.Source

/**
  * Created by jason on 16-4-13.
  */
class SelectorTest extends TestKit(ActorSystem.create("ClusterSystem", ConfigFactory.load("test.conf"))) with ImplicitSender
  with Matchers with FlatSpecLike {

  "parse JD" should "get correct content" in {
    val source = Source.fromURL(getClass.getResource("data/JD.html")).mkString
    val rule1 = new Rule(GET_CONTENT, List(new MatchRule(CSS, "li.gl-item")))
    val analyzeRequest: AnalyzeRequest = new AnalyzeRequest("test", 0, source, rule1, null)
    val contentSelector: ContentSelector = new ContentSelector("name", List(new MatchRule(XPATH, "//div[@class='p-name']/a/em/text()")))
    val dataSelector: ContentSelector = new ContentSelector("data", List(new MatchRule(XPATH, "//div[@class='j-sku-item']/@data-sku")))
    val contentSelectorList = List(contentSelector, dataSelector)
    rule1.contentSelectors = contentSelectorList
    val probe1 = TestProbe()
    val actor: ActorRef = system.actorOf(ProcessorActor.props(analyzeRequest.spiderId, analyzeRequest, probe1.ref))
    val response = probe1.expectMsgClass(100 seconds, classOf[AnalyzeResponse])
    println(response)

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
