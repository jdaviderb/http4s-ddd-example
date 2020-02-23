package server.services.tasks

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._

class ListTaskService {
  val service = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello World, $name.")
  }
}
