
name := """spider-model"""

version := "1.0"

lazy val akkaVersion = "2.4.0"

libraryDependencies ++= Dependencies.spider_cluster_seed

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")

fork in run := true
