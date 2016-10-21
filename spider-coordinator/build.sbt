
name := """spider-coordinator"""

version := "1.0"

lazy val akkaVersion = "2.4.0"

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")

fork in run := true

// the bash scripts classpath is in follow
scriptClasspath ~= (cp => "../conf" +: cp)

/** docker start */
packageName in Docker := packageName.value

version in Docker := version.value

dockerBaseImage := "java"

dockerAlias := DockerAlias(Some("spider"),None,packageName.value,Some(version.value))

dockerRepository := Some("index.tenxcloud.com/w926494698")

dockerExposedPorts := Seq(2553)

defaultLinuxInstallLocation in Docker := "/opt/docker"
/** docker end */

mappings in Universal <++= (packageBin in Compile, baseDirectory ) map { (_, target) =>
  val dir = target / "src" / "main" / "resources"
  (dir.*** --- dir) pair flatRebase("conf")
}
