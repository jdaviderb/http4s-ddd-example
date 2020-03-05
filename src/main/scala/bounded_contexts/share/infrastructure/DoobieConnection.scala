package bounded_contexts.share.infrastructure

import doobie._
import doobie.implicits._
import cats.effect.IO
import scala.concurrent.ExecutionContext
import conf.Settings

class DoobieConnection {
  implicit val cs = IO.contextShift(ExecutionContext.global)

  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    s"jdbc:postgresql:${Settings.postgresDatabase}",
    Settings.postgresUser,
    Settings.postgresPass
  )
}
