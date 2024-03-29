name := "akka-test"

version := "1.0"

scalaVersion := "2.12.6"

lazy val akkaVersion = "2.6.0-M2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-core" % "10.1.8",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8",
  "com.typesafe.akka" %% "akka-http" % "10.1.8",
  "com.typesafe.akka" %% "akka-http-xml" % "10.1.8",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.8" % "test",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "io.lettuce" % "lettuce-core" % "5.1.6.RELEASE",
  "net.debasishg" %% "redisclient" % "3.10"
)
