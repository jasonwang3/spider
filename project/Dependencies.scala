import sbt._

object Dependencies {

  object Versions {
    val akkaVersion = "2.4.0"
  }

  object Compile {

    import Versions._

    val akka_actor =      "com.typesafe.akka"   %% "akka-actor"          % akkaVersion
    val akka_testkit =    "com.typesafe.akka"   %% "akka-testkit"        % akkaVersion
    val akka_slf4j  =     "com.typesafe.akka"   %% "akka-slf4j"          % akkaVersion
    val scalatest =       "org.scalatest"       %% "scalatest"           % "2.2.4"      % "test"
    val junit =           "junit"               % "junit"                % "4.12"       % "test"
    val junit_interface = "com.novocode"        % "junit-interface"      % "0.11"       % "test"
    val logback =         "ch.qos.logback"      % "logback-classic"      % "1.1.3"
    val slf4f =           "org.slf4j"           % "slf4j-api"            % "1.7.13"
  }

  val spider_downloader = Seq(
    Compile.akka_actor,
    Compile.akka_slf4j,
    Compile.akka_testkit,
    Compile.scalatest,
    Compile.junit,
    Compile.junit_interface,
    Compile.logback,
    Compile.slf4f
  )

}
