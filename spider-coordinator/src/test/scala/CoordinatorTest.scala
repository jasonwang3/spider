import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import com.coordinator.actor.SpiderCoordinatorActor
import com.spider.model.{Site, Spider}
import com.spider.model.downloader.Request
import com.typesafe.config.ConfigFactory
import org.scalatest._

/**
  * Created by jason on 16-5-13.
  */
class CoordinatorTest extends TestKit(ActorSystem.create("ClusterSystem",ConfigFactory.load("test.conf"))) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "Coordinator actor" must {

    "process spider request" which {
      val spiderActor = system.actorOf(Props[SpiderCoordinatorActor], "downloadCoordinator")
      val request = new Request("https://github.com")
      val site = Site.create().setDomain(".github.com").addCookie("_ga", "GA1.2.328404385.1436499079")
        .addCookie(".github.com", "dotcom_user", "jasonwang3")
        .addCookie(".github.com", "logged_in", "yes")
        .addCookie("github.com", "tz", "Asia%2FShanghai")
      val spider = new Spider("testId", request, site, null)
      Thread.sleep(5000)
      spiderActor ! spider
      Thread.sleep(20000)

    }

  }


}
