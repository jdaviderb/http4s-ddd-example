package server

import cats.implicits._
import org.http4s.server.blaze._
import org.http4s.implicits._
import cats.effect._
import server.services.Services

object Server extends IOApp {
  val port = 8080
  val host = "localhost"

  def run(args: List[String]): IO[ExitCode] = {
    BlazeServerBuilder[IO]
      .bindHttp(port, host)
      .withHttpApp(Services.all.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}
