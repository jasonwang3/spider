import sbt._

object Dependencies {

  object Versions {
    val akkaVersion = "2.4.0"
  }

  object Compile {

    import Versions._

    val core_spring =     "com.spider.core"           % "core-akka-spring"     % "1.0-SNAPSHOT"

    val akka_actor =      "com.typesafe.akka"         %% "akka-actor"          % akkaVersion
    val akka_cluster =    "com.typesafe.akka"         %% "akka-cluster"        % akkaVersion
    val akka_testkit =    "com.typesafe.akka"         %% "akka-testkit"        % akkaVersion
    val akka_slf4j  =     "com.typesafe.akka"         %% "akka-slf4j"          % akkaVersion
    val scalatest =       "org.scalatest"             %% "scalatest"           % "2.2.6"      % "test"
    val scalactic =       "org.scalactic"             %% "scalactic"           % "2.2.6"
    val junit =           "junit"                     % "junit"                % "4.12"       % "test"
    val junit_interface = "com.novocode"              % "junit-interface"      % "0.11"       % "test"
    val logback =         "ch.qos.logback"            % "logback-classic"      % "1.1.3"
    val slf4f =           "org.slf4j"                 % "slf4j-api"            % "1.7.13"
    val guava =           "com.google.guava"          % "guava"                % "19.0"
    val httpcomponents =  "org.apache.httpcomponents" % "httpclient"           % "4.5.1"
    val commons_io =      "commons-io"                % "commons-io"           % "2.4"
    val spring_context =  "org.springframework"       % "spring-context"       % "4.2.4.RELEASE"
  }

  val spider_cluster_seed = Seq(
    Compile.akka_actor,
    Compile.akka_cluster,
    Compile.akka_slf4j,
    Compile.akka_testkit,
    Compile.scalatest,
    Compile.junit,
    Compile.junit_interface,
    Compile.logback,
    Compile.slf4f
  )


  val spider_downloader = Seq(
    Compile.akka_actor,
    Compile.akka_cluster,
    Compile.akka_slf4j,
    Compile.akka_testkit,
    Compile.scalatest,
    Compile.scalactic,
    Compile.logback,
    Compile.slf4f,
    Compile.spring_context,
    Compile.core_spring,
    Compile.commons_io
  )

  val spider_model = Seq(
    Compile.guava,
    Compile.httpcomponents
  )

}
