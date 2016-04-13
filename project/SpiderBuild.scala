import sbt.Keys._
import sbt._

object SpiderBuild extends Build {

  lazy val root = Project("spider", file(".")).aggregate(spider_model, spider_cluster_seed, spider_downloader)

  lazy val spider_cluster_seed = Project("spider-cluster-seed", file("spider-cluster-seed")).settings(
    scalaVersion := "2.11.7",
    libraryDependencies ++= Dependencies.spider_cluster_seed
  )

  lazy val spider_model = Project("spider-model", file("spider-model")).settings(
    scalaVersion := "2.11.7",
    libraryDependencies ++= Dependencies.spider_model
  )

  lazy val spider_downloader = Project("spider-downloader", file("spider-downloader")).settings(
    scalaVersion := "2.11.7",
    libraryDependencies ++= Dependencies.spider_downloader
  ).dependsOn(spider_model)

  lazy val spider_page_processor = Project("spider-page-processor", file("spider-page-processor")).settings(
    scalaVersion := "2.11.7",
    libraryDependencies ++= Dependencies.spider_page_processor
  ).dependsOn(spider_model)
}
