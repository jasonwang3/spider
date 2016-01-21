import akka.actor.ActorSystem
import akka.event.Logging
import org.slf4j.{LoggerFactory, Logger}

/**
  * Created by jason on 16-1-21.
  */


object DownloaderEngineMaster extends App {
  val logger = LoggerFactory.getLogger(classOf[DownloaderEngineMaster])
  val system = ActorSystem("downloaderEngine")

}

class DownloaderEngineMaster () {

}
