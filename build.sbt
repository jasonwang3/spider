
name := """spider"""

version := "1.0"


resolvers += {
  Resolver.mavenLocal
  Resolver.sonatypeRepo("public")
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
  "Typesafe Akka snapshot repository" at "http://repo.akka.io/snapshots/"
  "sonatype-snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases"
  Resolver.sbtPluginRepo("releases")
}

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")

fork in run := true
