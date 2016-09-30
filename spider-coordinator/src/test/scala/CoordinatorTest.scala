import akka.actor.{ActorSystem, Props}
import akka.cluster.Cluster
import akka.testkit.{ImplicitSender, TestKit}
import com.spider.coordinator.actor.SpiderCoordinatorActor
import com.spider.model.Action._
import com.spider.model.downloader.Request
import com.spider.model.selector.ContentSelector
import com.spider.model.support.SelectorType.{apply => _, _}
import com.spider.model.{Rule, Site, Spider}
import com.typesafe.config.ConfigFactory
import org.scalatest._

import scala.collection.mutable

/**
  * Created by jason on 16-5-13.
  */
class CoordinatorTest extends TestKit(ActorSystem.create("ClusterSystem", ConfigFactory.load("test.conf"))) with ImplicitSender
  with FunSuiteLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    Cluster.get(system).leave(Cluster.get(system).selfAddress)
    Cluster.get(system).down(Cluster.get(system).selfAddress)
    TestKit.shutdownActorSystem(system)
  }

  test("QA") {
    val sessionId = "018E65370B30D758D1C218D1DD6D263B"
    val spiderActor = system.actorOf(Props[SpiderCoordinatorActor], "downloadCoordinator")
    val request = new Request("http://219.238.188.179/seeyon/main.do?app=Coll&method=morePending4App&_spage=&page=1&pageSize=3")
    val site: Site = Site.create().setDomain("219.238.188.179").addCookie("219.238.188.179", "JSESSIONID", sessionId)
      .addCookie("219.238.188.179", "login.locale", "zh_CN")
      .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")
    //step 1
    var matchRule: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
    matchRule += CSS -> "div.bDiv"
    matchRule += LINK -> null
    matchRule += REGEX -> "/seeyon/collaboration\\.do\\?.*affairId=.*Pending"
    val rule1 = new Rule(GET_URL, matchRule)
    //step 2
    var matchRule2: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
    matchRule2 += XPATH -> "//frame[@id='detailMainFrame']/@src"
    val rule2 = new Rule(GET_URL, matchRule2)
    //step 3
    var matchRule3: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
    matchRule3 += XPATH -> "//iframe[@id='contentIframe']/@src"
    val rule3 = new Rule(GET_URL, matchRule3)
    //step 4
    val matchRule4: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
    matchRule4 += CSS -> "table.body-detail-form"
    matchRule4 += XPATH -> "script"
    val rule4 = new Rule(GET_CONTENT, matchRule4)
    /* content selector construe begin */
    val contentMap1: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
    contentMap1 += REGEX -> "(?<=<my:所属市场.{0,100}>).*?(?=</my:所属市场>)"
    val selector1: ContentSelector = new ContentSelector("market", contentMap1)
    val contentMap2: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
    contentMap2 += REGEX -> "(?<=<my:分公司新.{0,100}>).*?(?=</my:分公司新>)"
    val selector2: ContentSelector = new ContentSelector("newCompany", contentMap2)
    val contentMap3: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
    contentMap3 += REGEX -> "(?<=<my:申请日期.{0,100}>).*?(?=</my:申请日期>)"
    val selector3: ContentSelector = new ContentSelector("applyDate", contentMap3)
    val contentMap4: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
    contentMap4 += REGEX -> "(?<=<my:专柜名称.{0,100}>).*?(?=</my:专柜名称>)"
    val selector4: ContentSelector = new ContentSelector("counterName", contentMap4)
    val contentMap5: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
    contentMap5 += REGEX -> "(?<=<my:促销类别.{0,100}>).*?(?=</my:促销类别>)"
    val selector5: ContentSelector = new ContentSelector("PromotionCategory", contentMap5)
    val contentMap6: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
    contentMap6 += REGEX -> "(?<=<my:促销期间从.{0,100}>).*?(?=</my:促销期间从>)"
    val selector6: ContentSelector = new ContentSelector("beginDate", contentMap6)
    val contentMap7: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
    contentMap7 += REGEX -> "(?<=<my:促销期间到.{0,100}>).*?(?=</my:促销期间到>)"
    val selector7: ContentSelector = new ContentSelector("endDate", contentMap7)
    val contentMap8: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
    contentMap8 += REGEX -> "(?<=<my:促销方案.{0,100}>).*?(?=</my:促销方案>)"
    val selector8: ContentSelector = new ContentSelector("plan", contentMap8)
    val contentMap9: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
    contentMap9 += REGEX -> "(?<=<my:竞品促销活动情况.{0,100}>).*?(?=</my:竞品促销活动情况>)"
    val selector9: ContentSelector = new ContentSelector("situation", contentMap9)
    val contentSelectors: List[ContentSelector] = List(selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9)
    rule4.contentSelectors = contentSelectors
    /* content selector construe end */

    val rules: List[Rule] = List(rule1, rule2, rule3, rule4)
    val spider = new Spider("testId", request, site, rules)
    //send
    Thread.sleep(5000)
    spiderActor ! spider
    Thread.sleep(40000)
  }



  test("JD") {
    val spiderActor = system.actorOf(Props[SpiderCoordinatorActor], "downloadCoordinator")
    val site: Site = Site.create().setDomain("list.jd.com")
    val request = new Request("http://list.jd.com/list.html?cat=670,677,678&page=1&delivery=1")
    //step 1
    var matchRule: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
    matchRule += CSS -> "div#plist"
    val rule1 = new Rule(GET_CONTENT, matchRule)


//    val matchRuleForContent1: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
//    matchRuleForContent1 += CSS -> "div[data-sku]"
//    matchRuleForContent1 += XPATH -> "em"
//    val contentSelector1: ContentSelector = new ContentSelector("title", matchRuleForContent1)

    val rules: List[Rule] = List(rule1)
    val spider = new Spider("testJD", request, site, rules)
    //send
    Thread.sleep(5000)
    spiderActor ! spider
    Thread.sleep(40000)
  }

}
