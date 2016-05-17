import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import com.coordinator.actor.SpiderCoordinatorActor
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
    "process spider request" which {
      val spiderActor = system.actorOf(Props[SpiderCoordinatorActor], "downloadCoordinator")
      val request = new Request("http://219.238.188.179/seeyon/main.do?app=Coll&method=morePending4App&_spage=&page=1&pageSize=400")
      val site: Site = Site.create().setDomain("219.238.188.179").addCookie("219.238.188.179", "JSESSIONID", "E28E13DD3678EB7B7234E5BC533D4601")
        .addCookie("219.238.188.179", "login.locale", "zh_CN")
        .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")

      var matchRule: mutable.LinkedHashMap[SelectorType, String] = new mutable.LinkedHashMap[SelectorType, String]
      matchRule += CSS -> "div.bDiv"
      matchRule += LINK -> null
      matchRule += REGEX -> ".*/seeyon/collaboration.do?.*affairId=.*"
      val rule1 = new Rule(Download, matchRule)
      val rules: List[Rule] = List(rule1)
      val spider = new Spider("testId", request, site, rules)
      //send
      spiderActor ! spider
      Thread.sleep(30000)

    }

  }


}
