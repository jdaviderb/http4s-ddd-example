package server.services.tasks

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import bounded_contexts.tasks.application.DeleteTaskApplicationService

class DeleteTaskService {
  val taskService = DeleteTaskApplicationService
  val service = HttpRoutes.of[IO] {
    case DELETE -> Root / "tasks" / id =>
      for {
        _ <- taskService.delete(id.toInt)
        response <- Ok()
      } yield response
  }
}
