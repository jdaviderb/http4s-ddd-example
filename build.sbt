name := "api-example"

version := "0.1"

scalaVersion := "2.13.1"

lazy val doobieVersion = "0.8.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http"   % "10.1.11" ,
  "com.typesafe.akka" %% "akka-stream" % "2.5.26",
  "io.lemonlabs" %% "scala-uri" % "2.0.0",
  "org.tpolecat" %% "doobie-core"     % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2"   % doobieVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.26"  % "test",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.11"  % "test",
  "org.scalatest" %% "scalatest" % "3.1.0" % "test"
)