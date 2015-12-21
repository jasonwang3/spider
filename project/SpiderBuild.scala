import sbt._

object SpiderBuild extends Build{

  lazy val root = Project("spider",file(".")).aggregate(spider_downloader)

  lazy val spider_downloader = Project("spider-downloader",file("spider-downloader"))
}
