name := """spider-api"""

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

/** docker start */
packageName in Docker := packageName.value

version in Docker := version.value

dockerBaseImage := "java"

dockerRepository := Some("index.tenxcloud.com/w926494698")

dockerAlias := DockerAlias(Some("spider"),None,packageName.value,Some(version.value))

dockerExposedPorts := Seq(2552)

defaultLinuxInstallLocation in Docker := "/opt/docker"
/** docker end */

fork in run := true
