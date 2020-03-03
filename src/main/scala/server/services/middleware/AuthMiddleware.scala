package server.services.middleware

import cats.data.Kleisli
import cats.effect._
import org.http4s.headers.Authorization
import org.http4s._
import org.http4s.dsl.io._
import bounded_contexts.authentication.application.DecodeToken
import org.http4s.Status

object AuthMiddleware {
  def to(service: HttpRoutes[IO]): HttpRoutes[IO] = Kleisli { req: Request[IO] =>
    service(req).map {
      case Status.Successful(resp) =>
        val headers = req.headers.get(Authorization)
        val authError = Unauthorized(Challenge(scheme = "Bearer", realm = "Please enter a valid API key"))

        headers match {
          case Some(authHeader) =>
            val token = authHeader.value.replace("Bearer ", "")
            DecodeToken.decode(token) match {
              case Some(_) => resp
              case None => authError.unsafeRunSync()
            }
          case None => authError.unsafeRunSync()
        }
      case resp =>
        resp
    }
  }
}
