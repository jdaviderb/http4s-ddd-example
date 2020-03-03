package bounded_contexts.authentication.domain
import pdi.jwt.{JwtAlgorithm, JwtCirce}
import java.time.Clock

import bounded_contexts.share.domain.UserEntity
import cats.effect.IO
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe.jsonOf

import scala.util.{Failure, Success}

object AuthenticationService {
  implicit val decoder = jsonOf[IO, UserEntity]
  implicit val clock: Clock = Clock.systemUTC
  val defaultKey = "secretKey"

  def encodeToken(user: UserEntity, key: String = defaultKey): String =
    JwtCirce.encode(user.asJson, key, JwtAlgorithm.HS256)

  def decodeToken(token: String, key: String = defaultKey): Option[UserEntity] =
    JwtCirce.decodeJson(token, key, Seq(JwtAlgorithm.HS256)) match {
      case Success(v) => v.as[UserEntity].toOption
      case Failure(_) => None
    }
}