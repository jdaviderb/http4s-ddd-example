package server.services.authentication

import server.services.Services
import org.scalatest._
import org.http4s._
import cats.effect.IO
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import tests_helpers.TestsHelpers
import bounded_contexts.authentication.application.AuthenticateUser
import bounded_contexts.share.domain.UserEntity

class AuthenticateServiceTest extends FunSpec {
  val services = Services.all

  describe("/authenticate") {
    it("returns a jwt token") {
      val user = UserEntity("Jorge Hernandez")
      val body = user.asJson
      val request =  Request[IO](Method.POST, Uri(path = s"/authenticate")).withEntity(body)
      TestsHelpers.checkRequestAsJson(
        services,
        request
      ) { (status, body) =>
        assert(body == TokenSerializer(AuthenticateUser.authenticate(user)).asJson)
        assert(status == Status.Ok)
      }
    }
  }
}