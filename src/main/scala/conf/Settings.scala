package conf

object Settings {
  val env = scala.util.Properties.envOrElse("SCALA_ENV", "")

  /* POSTGRESQL CONFIG */

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

  /* JWT CONFIG */

  val apiJwtSecret = scala.util.Properties.envOrElse("API_JWT_SECRET", "secretKey")
}
