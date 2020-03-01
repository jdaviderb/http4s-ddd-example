package server.services.tasks

import bounded_contexts.tasks.domain.TaskEntity
import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import bounded_contexts.tasks.application.CreateTaskApplicationService

class CreateTaskService {
  implicit val decoder = jsonOf[IO, TaskEntity]
  val taskService = CreateTaskApplicationService
  val service = HttpRoutes.of[IO] {
    case req @ POST -> Root / "tasks" =>
      for {
        taskRequest <- req.as[TaskEntity]
        task <- taskService.create(taskRequest)
        response <- Ok(task.asJson)
      } yield response
  }
}
