import sbt._

object Dependencies {

  object Versions {
    val akkaVersion = "2.4.0"
  }

  object Compile {

    import Versions._

    val akka_actor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
    val akka_testkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion
    val scalatest = "org.scalatest" %% "scalatest" % "2.2.4" % "test"
    val junit = "junit" % "junit" % "4.12" % "test"
    val junit_interface = "com.novocode" % "junit-interface" % "0.11" % "test"
    val spring_amqp = "org.springframework.amqp" % "spring-amqp" % "1.5.3.RELEASE"
    val spring_rabbit = "org.springframework.amqp" % "spring-rabbit" % "1.5.3.RELEASE"
  }

  val spider_downloader = Seq(
    Compile.akka_actor,
    Compile.akka_testkit,
    Compile.scalatest,
    Compile.junit,
    Compile.junit_interface,
    Compile.spring_amqp,
    Compile.spring_rabbit
  )

}
