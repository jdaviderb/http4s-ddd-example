package server.services.authentication
import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import bounded_contexts.authentication.application.AuthenticateUser
import bounded_contexts.share.domain.UserEntity

class AuthenticateService {
  implicit val decoder = jsonOf[IO, UserEntity]
  val service = HttpRoutes.of[IO] {
    case req @ POST -> Root / "authenticate" =>
      for {
        userRequest <- req.as[UserEntity]
        token <- IO(AuthenticateUser.authenticate(userRequest))
        response <- Ok(TokenSerializer(token).asJson)
      } yield response
  }
}
