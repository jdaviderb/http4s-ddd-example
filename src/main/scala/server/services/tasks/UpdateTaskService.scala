package server.services.tasks

import bounded_contexts.tasks.domain.TaskEntity
import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.circe.jsonOf
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import bounded_contexts.tasks.application.UpdateTaskApplicationService
import bounded_contexts.tasks.application.FindTaskApplicationService

class UpdateTaskService {
  implicit val decoder = jsonOf[IO, TaskEntity]

  val service = HttpRoutes.of[IO] {
    case req @ PUT -> Root / "tasks" / id =>
      for {
        task <- FindTaskApplicationService.find(id.toInt)
        taskRequest <- req.as[TaskEntity].map { x =>
          x.copy(id = task.get.id)
        }
        taskUpdated <- UpdateTaskApplicationService.update(taskRequest)
        response <- Ok(taskUpdated.asJson)
      } yield response
  }
}
