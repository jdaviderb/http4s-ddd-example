package bounded_contexts.share.infrastructure

import doobie._
import doobie.implicits._
import cats.effect.IO
import scala.concurrent.ExecutionContext

class DoobieConnection {
  implicit val cs = IO.contextShift(ExecutionContext.global)

  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    s"jdbc:postgresql:${scala.sys.env("POSTGRES_DATABASE_NAME")}",
    scala.sys.env("POSTGRES_DATABASE_USER"),
    scala.sys.env("POSTGRES_DATABASE_PASS")
  )
}
