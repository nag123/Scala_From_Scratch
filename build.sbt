ThisBuild / version := "0.1.0-SNAPSHOT"

name := "Scala_From_Scratch"
version := "0.1"

scalaVersion := "2.12.10"

val sparkVersion = "3.0.2"
val postgresVersion = "42.2.2"
val cassandraConnectorVersion = "3.0.0" // preview version at the moment of writing (July 7, 2020)
val akkaVersion = "2.5.24"
val akkaHttpVersion = "10.1.7"
val twitter4jVersion = "4.0.7"
val kafkaVersion = "2.4.0"
val log4jVersion = "2.4.1"
val nlpLibVersion = "3.5.1"


/*
  Beware that if you're working on this repository from a work computer,
  corporate firewalls might block the IDE from downloading the libraries and/or the Docker images in this project.
 */
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,

  // streaming
  "org.apache.spark" %% "spark-streaming" % sparkVersion,

  // streaming-kafka
  "org.apache.spark" % "spark-sql-kafka-0-10_2.12" % sparkVersion,

  // low-level integrations
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kinesis-asl" % sparkVersion,

  "com.datastax.spark" %% "spark-cassandra-connector" % cassandraConnectorVersion,

  // postgres
  "org.postgresql" % "postgresql" % postgresVersion,

  // logging
  "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-core" % log4jVersion,

  // kafka
  "org.apache.kafka" %% "kafka" % kafkaVersion,
  "org.apache.kafka" % "kafka-streams" % kafkaVersion,

  "com.typesafe.akka" % "akka-actor-typed_2.12" % "2.6.20",
  "com.typesafe.akka" % "akka-stream_2.12" % "2.6.20",
  "com.typesafe.akka" % "akka-stream-testkit_2.12" % "2.6.20",
  "com.typesafe.akka" % "akka-http_2.12" % "10.2.8",

  "com.lihaoyi" %% "fansi" % "0.2.3"

)
