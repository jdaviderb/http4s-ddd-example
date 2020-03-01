name := "api-example"

version := "0.1"

scalaVersion := "2.13.1"

lazy val doobieVersion = "0.8.8"
lazy val http4sVersion = "0.21.0"
lazy val log4J2Version    = "2.6.2"

enablePlugins(FlywayPlugin)

libraryDependencies ++= Seq(
  "io.lemonlabs" %% "scala-uri" % "2.0.0",
  "org.slf4j" % "slf4j-simple" % "1.6.4",
  "org.jooq" % "jooq" % "3.13.1",
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-generic" % "0.13.0",
  "io.circe" %% "circe-literal" % "0.13.0",
  "org.tpolecat" %% "doobie-core"     % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2"   % doobieVersion,
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.scalatest" %% "scalatest" % "3.1.0" % "test"
)

flywayUrl := s"jdbc:postgresql:${scala.util.Properties.envOrElse("POSTGRES_DATABASE_NAME", "world")}"
flywayUser := scala.util.Properties.envOrElse("POSTGRES_DATABASE_USER", "postgres")
flywayPassword := scala.util.Properties.envOrElse("POSTGRES_DATABASE_PASS", "")
flywayLocations += "db/migration"
coverageEnabled := true
coverageHighlighting := true
