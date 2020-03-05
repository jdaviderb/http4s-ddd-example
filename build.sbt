name := "api-example"

version := "0.1"

scalaVersion := "2.13.1"

envVars in Test := Map("SCALA_ENV" -> "test")
fork in Test := true

val doobieVersion = "0.8.8"
val http4sVersion = "0.21.0"
val circeVersion = "0.13.0"
val jwtVersion = "4.2.0"

libraryDependencies ++= Seq(
  "com.pauldijou" %% "jwt-core" % jwtVersion,
  "com.pauldijou" %% "jwt-circe" % jwtVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-literal" % circeVersion,
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2" % doobieVersion,
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.slf4j" % "slf4j-simple" % "1.6.4",
  "org.jooq" % "jooq" % "3.13.1",
  "io.lemonlabs" %% "scala-uri" % "2.0.0",
  "org.scalatest" %% "scalatest" % "3.1.0" % "test"
)

coverageEnabled := true
coverageHighlighting := true
Test / parallelExecution := false

/* FLYWAY CONFIG */

enablePlugins(FlywayPlugin)

val env = scala.util.Properties.envOrElse("SCALA_ENV", "")


val postgresDatabase = env match {
  case "test" => scala.util.Properties.envOrElse("API_POSTGRES_TEST_DATABASE", "http4s_api_test")
  case _ => scala.util.Properties.envOrElse("API_POSTGRES_DATABASE", "http4s_api")
}

val postgresUser = env match {
  case "test" => scala.util.Properties.envOrElse("API_POSTGRES_TEST_USER", "postgres")
  case _ => scala.util.Properties.envOrElse("API_POSTGRES_USER", "postgres")
}

val postgresPass = env match {
  case "test" => scala.util.Properties.envOrElse("API_POSTGRES_TEST_PASS", "")
  case _ => scala.util.Properties.envOrElse("API_POSTGRES_PASS", "")
}

flywayUrl := s"jdbc:postgresql:${postgresDatabase}"
flywayUser := postgresUser
flywayPassword := postgresPass
flywayLocations += "db/migration"
