name := """spider-api"""

version := "1.0"

scalaVersion := "2.11.7"

enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


fork in run := true