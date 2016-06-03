import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import com.spider.coordinator.actor.SpiderCoordinatorActor
import com.spider.model.downloader.Request
import com.spider.model.{Rule, Site, Spider}
import com.spider.model.Action._
import com.spider.model.support.SelectorType.{apply => _, _}
import com.typesafe.config.ConfigFactory
import org.scalatest._

import scala.collection.mutable

/**
  * Created by jason on 16-5-13.
  */
class CoordinatorTest extends TestKit(ActorSystem.create("ClusterSystem", ConfigFactory.load("test.conf"))) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "Coordinator actor" must {
    "process OA request" which {
      val spiderActor = system.actorOf(Props[SpiderCoordinatorActor], "downloadCoordinator")
      val request = new Request("http://219.238.188.179/seeyon/main.do?app=Coll&method=morePending4App&_spage=&page=1&pageSize=5")
      val site: Site = Site.create().setDomain("219.238.188.179").addCookie("219.238.188.179", "JSESSIONID", "931E574CFA46707CE91D986C605C81A5")
        .addCookie("219.238.188.179", "login.locale", "zh_CN")
        .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")
      var matchRule: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
      matchRule += CSS -> "div.bDiv"
      matchRule += LINK -> null
      matchRule += REGEX -> "/seeyon/collaboration\\.do\\?.*affairId=.*Pending"
      val rule1 = new Rule(Download, matchRule)
      var matchRule2: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
      matchRule2 += XPATH -> "//frame[@id='detailMainFrame']/@src"
      val rule2 = new Rule(Download, matchRule2)

      val rules: List[Rule] = List(rule1, rule2)
      val spider = new Spider("testId", request, site, rules)
      //send
      Thread.sleep(5000)
      spiderActor ! spider
      Thread.sleep(40000)

    }

  }


}
