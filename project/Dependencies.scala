import play.sbt.PlayImport._
import sbt._

object Dependencies {

  object Versions {
    val akkaVersion = "2.4.12"
  }

  object Compile {

    import Versions._

    val core_spring = "com.spider.core" % "core-akka-spring" % "1.0-SNAPSHOT"

    val akka_actor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
    val akka_cluster = "com.typesafe.akka" %% "akka-cluster" % akkaVersion
    val akka_testkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion
    val akka_slf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
    val akka_cluster_tool = "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion
    val scalatest = "org.scalatest" %% "scalatest" % "2.2.6" % "test"
    val scalactic = "org.scalactic" %% "scalactic" % "2.2.6"
    val junit = "junit" % "junit" % "4.12" % "test"
    val junit_interface = "com.novocode" % "junit-interface" % "0.11" % "test"
    val logback_core = "ch.qos.logback" % "logback-core" % "1.1.6"
    val logback = "ch.qos.logback" % "logback-classic" % "1.1.6"
    val slf4f = "org.slf4j" % "slf4j-api" % "1.7.13"
    val logback_encoder = "net.logstash.logback" % "logstash-logback-encoder" % "4.7"
    val guava = "com.google.guava" % "guava" % "19.0"
    val httpcomponents = "org.apache.httpcomponents" % "httpclient" % "4.5.1"
    val commons_io = "commons-io" % "commons-io" % "2.4"
    val commons_lang3 = "org.apache.commons" % "commons-lang3" % "3.4"
    val commons_collection = "commons-collections" % "commons-collections" % "3.2.2"
    val spring_context = "org.springframework" % "spring-context" % "4.2.4.RELEASE"
    val jsoup = "org.jsoup" % "jsoup" % "1.8.3"
    val xsoup = "us.codecraft" % "xsoup" % "0.3.1"
    val poi = "org.apache.poi" % "poi" % "3.14"
    val fastjson = "com.alibaba" % "fastjson" % "1.2.12"
    val jackson = "com.fasterxml.jackson.core" % "jackson-core" % "2.8.4"
    val json4s = "org.json4s" %% "json4s-jackson" % "3.4.2"
    val json4s_ext = "org.json4s" %% "json4s-ext" % "3.4.2"
    val swagger_core = "io.swagger" % "swagger-core" % "1.5.10"
    val swagger_annotations = "io.swagger" % "swagger-annotations" % "1.5.10"
    val swagger_models = "io.swagger" % "swagger-models" % "1.5.10"
    val scalatestplus_play = "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
    val akka = Seq(akka_actor, akka_cluster, akka_slf4j, akka_testkit, akka_cluster_tool)
    val swagger = Seq(swagger_core, swagger_annotations, swagger_models)
  }


  val spider_cluster_seed = Compile.akka ++ Seq(
    Compile.scalatest,
    Compile.junit,
    Compile.junit_interface,
    Compile.logback,
    Compile.logback_core,
    Compile.slf4f,
    Compile.logback_encoder
  )


  val spider_downloader = Compile.akka ++ Seq(
    Compile.scalatest,
    Compile.scalactic,
    Compile.logback,
    Compile.logback_core,
    Compile.slf4f,
    Compile.logback_encoder,
    Compile.spring_context,
    Compile.core_spring,
    Compile.commons_io,
    Compile.commons_lang3,
    Compile.commons_collection,
    Compile.jsoup,
    Compile.xsoup
  )

  val spider_model = Seq(
    Compile.guava,
    Compile.httpcomponents
  )

  val spider_page_processor = Compile.akka ++ Seq(
    Compile.scalatest,
    Compile.scalactic,
    Compile.logback,
    Compile.logback_core,
    Compile.slf4f,
    Compile.logback_encoder,
    Compile.spring_context,
    Compile.core_spring,
    Compile.jsoup,
    Compile.xsoup,
    Compile.commons_io,
    Compile.commons_lang3,
    Compile.commons_collection,
    Compile.poi,
    Compile.fastjson
  )

  val spider_api = Compile.akka ++ Compile.swagger ++ Seq(
    jdbc,
    cache,
    ws,
    Compile.scalatestplus_play,
    Compile.logback_encoder,
    Compile.logback,
    Compile.logback_core,
    Compile.json4s,
    Compile.json4s_ext
  )

  val spider_coordinator = Compile.akka ++ Seq(
    Compile.scalatest,
    Compile.scalactic,
    Compile.logback,
    Compile.logback_core,
    Compile.slf4f,
    Compile.logback_encoder,
    Compile.spring_context,
    Compile.core_spring,
    Compile.commons_io,
    Compile.commons_lang3,
    Compile.commons_collection
  )

}
