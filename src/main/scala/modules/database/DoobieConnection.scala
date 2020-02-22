package modules.database

import doobie._
import doobie.implicits._
import cats.effect.IO
import scala.concurrent.ExecutionContext

class DoobieConnection {

  implicit val cs = IO.contextShift(ExecutionContext.global)
  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", "jdbc:postgresql:world", "postgres", ""
  )


}
