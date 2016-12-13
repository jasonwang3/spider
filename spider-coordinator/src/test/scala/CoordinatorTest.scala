import akka.actor.{ActorSystem, Props}
import akka.cluster.Cluster
import akka.testkit.{ImplicitSender, TestKit}
import com.spider.coordinator.actor.SpiderCoordinatorActor
import com.spider.model.Action._
import com.spider.model.downloader.Request
import com.spider.model.selector.ContentSelector
import com.spider.model.support.SelectorType.{apply => _, _}
import com.spider.model.{MatchRule, Rule, Site, Spider}
import com.typesafe.config.ConfigFactory
import org.scalatest._

import scala.collection.mutable

/**
  * Created by jason on 16-5-13.
  */
class CoordinatorTest extends TestKit(ActorSystem.create("ClusterSystem", ConfigFactory.load("test.conf"))) with ImplicitSender
  with FunSuiteLike with Matchers with BeforeAndAfterAll {

  override def beforeAll() = {
    System.setProperty("SEED_HOST", "127.0.0.1")
  }

  override def afterAll {
    Cluster.get(system).down(Cluster.get(system).selfAddress)
    TestKit.shutdownActorSystem(system)
  }

  test("QA") {
    val sessionId = "018E65370B30D758D1C218D1DD6D263B"
    val spiderActor = system.actorOf(Props[SpiderCoordinatorActor], "downloadCoordinator")
    val request = new Request("http://219.238.188.179/seeyon/main.do?app=Coll&method=morePending4App&_spage=&page=1&pageSize=3")
    val site: Site = new Site("219.238.188.179")
      .addCookie("219.238.188.179", "JSESSIONID", sessionId)
      .addCookie("219.238.188.179", "login.locale", "zh_CN")
      .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")
    //step 1
    val rule1 = new Rule(GET_URL, List(new MatchRule(CSS, "div.bDiv"), new MatchRule(LINK, null), new MatchRule(REGEX, "/seeyon/collaboration\\.do\\?.*affairId=.*Pending")))
    //step 2
    val rule2 = new Rule(GET_URL, List(new MatchRule(XPATH, "//frame[@id='detailMainFrame']/@src")))
    //step 3
    val rule3 = new Rule(GET_URL, List(new MatchRule(XPATH, "//iframe[@id='contentIframe']/@src")))
    //step 4
    val matchRule4: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
    val rule4 = new Rule(GET_CONTENT, List(new MatchRule(CSS, "table.body-detail-form"), new MatchRule(XPATH, "script")))
    /* content selector construe begin */
    val selector1: ContentSelector = new ContentSelector("market", List(new MatchRule(REGEX, "(?<=<my:所属市场.{0,100}>).*?(?=</my:所属市场>)")))
    val selector2: ContentSelector = new ContentSelector("newCompany", List(new MatchRule(REGEX, "(?<=<my:分公司新.{0,100}>).*?(?=</my:分公司新>)")))
    val selector3: ContentSelector = new ContentSelector("applyDate", List(new MatchRule(REGEX, "(?<=<my:申请日期.{0,100}>).*?(?=</my:申请日期>)")))
    val selector4: ContentSelector = new ContentSelector("counterName", List(new MatchRule(REGEX, "(?<=<my:专柜名称.{0,100}>).*?(?=</my:专柜名称>)")))
    val selector5: ContentSelector = new ContentSelector("PromotionCategory", List(new MatchRule(REGEX, "(?<=<my:促销类别.{0,100}>).*?(?=</my:促销类别>)")))
    val selector6: ContentSelector = new ContentSelector("beginDate", List(new MatchRule(REGEX, "(?<=<my:促销期间从.{0,100}>).*?(?=</my:促销期间从>)")))
    val selector7: ContentSelector = new ContentSelector("endDate", List(new MatchRule(REGEX, "(?<=<my:促销期间到.{0,100}>).*?(?=</my:促销期间到>)")))
    val selector8: ContentSelector = new ContentSelector("plan", List(new MatchRule(REGEX, "(?<=<my:促销方案.{0,100}>).*?(?=</my:促销方案>)")))
    val selector9: ContentSelector = new ContentSelector("situation", List(new MatchRule(REGEX, "(?<=<my:竞品促销活动情况.{0,100}>).*?(?=</my:竞品促销活动情况>)")))
    val contentSelectors: List[ContentSelector] = List(selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9)
    rule4.contentSelectors = contentSelectors
    /* content selector construe end */
    val rules: List[Rule] = List(rule1, rule2, rule3, rule4)
    val spider = new Spider(request, site, rules)
    spider.id = "testId"
    //send
    Thread.sleep(5000)
    spiderActor ! spider
    Thread.sleep(40000)
  }



  test("JD") {
    val spiderActor = system.actorOf(Props[SpiderCoordinatorActor], "downloadCoordinator")
    val site: Site = new Site("list.jd.com")
    val request = new Request("http://list.jd.com/list.html?cat=670,677,678&page=1&delivery=1")
    //step 1
    val rule1 = new Rule(GET_CONTENT, List(new MatchRule(CSS, "li.gl-item")))
    val contentSelector: ContentSelector = new ContentSelector("name", List(new MatchRule(XPATH, "//div[@class='p-name']/a/em/text()")))
    val dataSelector: ContentSelector = new ContentSelector("data", List(new MatchRule(XPATH, "//div[@class='j-sku-item']/@data-sku")))
    val contentSelectorList = List(contentSelector, dataSelector)
    rule1.contentSelectors = contentSelectorList

    val rules: List[Rule] = List(rule1)
    val spider = new Spider(request, site, rules)
    spider.id = "testJD"
    //send
    Thread.sleep(5000)
    spiderActor ! spider
    Thread.sleep(40000)
  }

  test("TMALL") {
    val spiderActor = system.actorOf(Props[SpiderCoordinatorActor], "downloadCoordinator")
    val site: Site = new Site("list.tmall.com")
    site.charset = "GBK"
    val request = new Request("https://list.tmall.com/search_product.htm?q=%E6%9B%BC%E5%A6%AE%E8%8A%AC&imgfile=&commend=all&ssid=s5-e&search_type=tmall&sourceId=tb.index&spm=a21bo.50862.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20161114")
    val rule1 = new Rule(GET_CONTENT, List(new MatchRule(CSS, "div.product")))
    val titleSelector: ContentSelector = new ContentSelector("title", List(new MatchRule(XPATH, "//p[@class='productTitle']/a/@title")))
    val priceSelector: ContentSelector = new ContentSelector("price", List(new MatchRule(XPATH, "//p[@class='productPrice']/em/@title")))
    val imageSelector: ContentSelector = new ContentSelector("image", List(new MatchRule(XPATH, "//div[@class='productImg-wrap']/a/img/@src|//div[@class='productImg-wrap']/a/img/@data-ks-lazyload")))
    imageSelector.urlDownload = true
    val contentSelectorList = List(titleSelector, priceSelector, imageSelector)
    rule1.contentSelectors = contentSelectorList
    val rules: List[Rule] = List(rule1)
    val spider = new Spider(request, site, rules)
    spider.id = "testTMALL"
    //send
    Thread.sleep(5000)
    spiderActor ! spider
    Thread.sleep(40000)
  }

}
