ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion :="2.13.5"

name := "Scala_From_Scratch"

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor-typed_2.13" % "2.6.20",
  "com.typesafe.akka" % "akka-stream_2.13" % "2.6.20",
  "com.typesafe.akka" % "akka-stream-testkit_2.13" % "2.6.20",
  "com.typesafe.akka" % "akka-http_2.13" % "10.2.8"
)
