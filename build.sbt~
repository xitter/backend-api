import _root_.sbt.Keys._
import _root_.scala._
import play.Project._

name := "backend_api"

version := "1.0"


libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "org.elasticsearch" % "elasticsearch" % "1.2.1"
)

play.Project.playJavaSettings

findbugsSettings

jacoco.settings
