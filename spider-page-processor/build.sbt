
name := """spider-page-processor"""

version := "1.0"

lazy val akkaVersion = "2.4.0"

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")

fork in run := true

// the bash scripts classpath is in follow
scriptClasspath ~= (cp => "../conf" +: cp)

mappings in Universal <++= (packageBin in Compile, baseDirectory ) map { (_, target) =>
  val dir = target / "src" / "main" / "resources"
  (dir.*** --- dir) pair flatRebase("conf")
}
