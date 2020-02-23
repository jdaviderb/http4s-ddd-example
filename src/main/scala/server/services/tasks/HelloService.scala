package server.services.tasks

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._

import modules.database.DoobieConnection
import modules.tasks.TaskRepository

class HelloService {
  val service = HttpRoutes.of[IO] {
    case GET -> Root / "hello" =>
      val connection = new DoobieConnection()
      val repository = new TaskRepository(connection)

      for {
        task <- repository.task
        response <- Ok(task.asJson)
      } yield response
  }
}
