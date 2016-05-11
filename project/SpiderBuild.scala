import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
import com.typesafe.sbt.packager.linux.LinuxPlugin
import sbt.Keys._
import sbt._
import sbt.dsl._

object SpiderBuild extends Build {

  lazy val root = Project("spider", file(".")).aggregate(spider_model, spider_cluster_seed, spider_downloader, spider_api, spider_coordinator)
    .enablePlugins(JavaAppPackaging)

  lazy val spider_cluster_seed = Project("spider-cluster-seed", file("spider-cluster-seed")).settings(
    scalaVersion := "2.11.7",
    libraryDependencies ++= Dependencies.spider_cluster_seed
  ).enablePlugins(JavaAppPackaging).enablePlugins(LinuxPlugin)

  lazy val spider_model = Project("spider-model", file("spider-model")).settings(
    scalaVersion := "2.11.7",
    libraryDependencies ++= Dependencies.spider_model
  ).enablePlugins(JavaAppPackaging).enablePlugins(LinuxPlugin)

  lazy val spider_downloader = Project("spider-downloader", file("spider-downloader")).settings(
    scalaVersion := "2.11.7",
    libraryDependencies ++= Dependencies.spider_downloader
  ).dependsOn(spider_model).enablePlugins(JavaAppPackaging).enablePlugins(LinuxPlugin)

  lazy val spider_page_processor = Project("spider-page-processor", file("spider-page-processor")).settings(
    scalaVersion := "2.11.7",
    libraryDependencies ++= Dependencies.spider_page_processor
  ).dependsOn(spider_model).enablePlugins(JavaAppPackaging).enablePlugins(LinuxPlugin)

  lazy val spider_api = Project("spider-api", file("spider-api")).settings(
    scalaVersion := "2.11.7",
    libraryDependencies ++= Dependencies.spider_api
  ).dependsOn(spider_model).enablePlugins(JavaAppPackaging).enablePlugins(LinuxPlugin)

  lazy val spider_coordinator = Project("spider-coordinator", file("spider-coordinator")).settings(
    scalaVersion := "2.11.7",
    libraryDependencies ++= Dependencies.spider_coordinator
  ).dependsOn(spider_model).enablePlugins(JavaAppPackaging).enablePlugins(LinuxPlugin)
}
