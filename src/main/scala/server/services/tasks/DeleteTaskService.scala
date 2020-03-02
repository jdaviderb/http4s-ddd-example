package server.services.tasks

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe._
import bounded_contexts.tasks.application.DeleteTaskApplicationService
import server.services.helpers.MessageError

class DeleteTaskService {
  val taskService = DeleteTaskApplicationService
  val service = HttpRoutes.of[IO] {
    case DELETE -> Root / "tasks" / id =>
      for {
        _ <- taskService.delete(id.toInt)
        response <- Ok(MessageError("ok").asJson)
      } yield response
  }
}
