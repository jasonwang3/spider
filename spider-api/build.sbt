
name := """spider-api"""

version := "1.0"

scalaVersion := "2.11.7"


enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


fork in run := true
