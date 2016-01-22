import sbt._

object SpiderBuild extends Build{

  lazy val root = Project("spider",file(".")).aggregate(spider_model,spider_cluster_seed,spider_downloader)

  lazy val spider_cluster_seed = Project("spider-cluster-seed",file("spider-cluster-seed"))

  lazy val spider_model = Project("spider-model",file("spider-model"))

  lazy val spider_downloader = Project("spider-downloader",file("spider-downloader"))
}
