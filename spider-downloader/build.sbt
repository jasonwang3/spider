
name := """spider-downloader"""

version := "1.0"

scalaVersion := "2.11.7"

lazy val akkaVersion = "2.4.0"

libraryDependencies ++= Dependencies.spider_downloader

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")


fork in run := true
