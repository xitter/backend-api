import _root_.sbt._
import _root_.sbt.Keys._
import _root_.sbt.Level
import _root_.scala._

// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.3")

libraryDependencies ++= Seq(
  "com.puppycrawl.tools" % "checkstyle" % "5.5"
)

addSbtPlugin("de.johoop" % "jacoco4sbt" % "2.1.6")

addSbtPlugin("de.johoop" % "findbugs4sbt" % "1.3.0")