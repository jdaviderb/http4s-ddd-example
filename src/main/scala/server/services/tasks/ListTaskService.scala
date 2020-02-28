package server.services.tasks

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import bounded_contexts.tasks.public.FilterTaskApplicationService

class ListTaskService {
  val service = HttpRoutes.of[IO] {
    case GET -> Root / "tasks" =>
      for {
        tasks <- FilterTaskApplicationService.filter
        response <- Ok(tasks.asJson)
      } yield response
  }
}
