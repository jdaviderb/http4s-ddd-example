package server.services.tasks

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import bounded_contexts.tasks.application.FindTaskApplicationService
import server.services.helpers.Message

class GetTaskService {
  val taskService = FindTaskApplicationService

  val service = HttpRoutes.of[IO] {
    case GET -> Root / "tasks" / id =>
      for {
        task <- taskService.find(id.toInt)
        response <- if (task == None) NotFound(Message("Not found").asJson)
        else Ok(task.asJson)
      } yield response
  }
}
