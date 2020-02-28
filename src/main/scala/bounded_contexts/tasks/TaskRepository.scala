package bounded_contexts.tasks

import cats.effect.IO
import bounded_contexts.share.infrastructure.DoobieConnection
import doobie._
import doobie.implicits._
import scala.util.chaining._

import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.impl.DSL._

class TaskRepository(connection: DoobieConnection) {
  val queryBuilder =  DSL.using(SQLDialect.POSTGRES)

  def find(id: Int): IO[Option[TaskEntity]] =
    queryBuilder
      .select(field("id"), field("title"), field("done"))
      .from(table("tasks"))
      .where(field("id").equal(id))
      .limit(1)
      .getSQL(true)
      .pipe(Fragment.const(_))
      .query[TaskEntity]
      .option
      .transact(connection.xa)


  def update(task: TaskEntity): IO[TaskEntity] =
    queryBuilder
      .update(table("tasks"))
      .set(field("title"), task.title)
      .where(field("id").equal(task.id.get))
      .getSQL(true)
      .pipe(Fragment.const(_))
      .update
      .run
      .transact(connection.xa)
      .map { _ => task.copy() }

  def create(task: TaskEntity): IO[TaskEntity] =
    queryBuilder
      .insertInto(table("tasks"), field("title"), field("done"))
      .values(task.title, task.done)
      .getSQL(true)
      .pipe(Fragment.const(_))
      .update
      .withUniqueGeneratedKeys[Int]("id")
      .transact(connection.xa)
      .map { id => task.copy(id = Some(id)) }

  def delete(id: Int): IO[Unit] =
    queryBuilder
      .deleteFrom(table("tasks"))
      .where(field("id").equal(id))
      .getSQL(true)
      .pipe(Fragment.const(_))
      .update
      .run
      .transact(connection.xa)
      .map { _ => () }

  def all: IO[List[TaskEntity]] =
    queryBuilder
        .select(field("id"), field("title"), field("done"))
        .from(table("tasks"))
        .getSQL
        .pipe(Fragment.const(_))
        .query[TaskEntity]
        .to[List]
        .transact(connection.xa)
}
