package modules.tasks

import cats.effect.IO
import modules.database.DoobieConnection
import doobie._
import doobie.implicits._

class TaskRepository(connection: DoobieConnection) {
  def task(): IO[Option[Task]] = {
    sql"select * from tasks".query[Task].option.transact(connection.xa)
  }
}
