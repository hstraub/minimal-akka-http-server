enablePlugins( JavaServerAppPackaging )

lazy val commonSettings = Seq(
  organization := "at.linuxhacker",
  version := "0.1",
  scalaVersion := "2.11.6",
  name := "minimal-akka-http-server"
)

mainClass in Compile := Some( "at.linuxhacker.minimalHttpServer.Test" )

lazy val root = (project in file(".")).
  settings(commonSettings: _*)
  
maintainer in Linux := "Herbert Straub <herbert@linuxhacker.at>"

packageSummary in Linux := "Custom application configuration"

packageDescription := "Custom application configuration"

rpmVendor := "Herbert Straub"

rpmLicense := Some( "GNU/GPLv3" )

mappings in Universal <+= (packageBin in Compile, sourceDirectory ) map { (_, src) =>
    // we are using the reference.conf as default application.conf
    // the user can override settings here
    val conf = src / "main" / "resources" / "application.conf"
    conf -> "conf/application.conf"
}

//javaOptions in Universal ++= Seq("-Dconfig.file=${{app_home}}/conf/application.conf")

resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases" at "http://oss.sonatype.org/content/repositories/releases",
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases" )
  
  
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-experimental" % "1.0-M2",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "1.0-M2"
)


