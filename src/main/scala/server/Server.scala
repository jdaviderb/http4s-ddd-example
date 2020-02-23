package server

import cats.implicits._
import org.http4s.server.blaze._
import org.http4s.implicits._
import cats.effect._

object Server extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(Services.all.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}