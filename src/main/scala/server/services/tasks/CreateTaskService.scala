package server.services.tasks

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import bounded_contexts.tasks.public.CreateTaskApplicationService
import bounded_contexts.tasks.TaskEntity

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
