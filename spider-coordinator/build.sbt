
name := """spider-coordinator"""

version := "1.0"

lazy val akkaVersion = "2.4.0"

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")

fork in run := true
