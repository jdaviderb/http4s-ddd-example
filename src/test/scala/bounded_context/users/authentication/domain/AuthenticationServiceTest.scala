package bounded_context.users.authentication.domain

import bounded_contexts.share.domain.UserEntity
import org.scalatest.FunSpec
import bounded_contexts.authentication.domain.AuthenticationService
class AuthenticationServiceTest extends FunSpec {
  val jwtSecret = "test"
  val expectedToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiSm9yZ2UgSGVybmF" ++
      "uZGV6In0.4jfeqhG6sPafsqiWI9mvu-XujZXuAqxECCAe0cD0TO0"

  describe("#encodeToken") {
    it("encondes a user as jwt") {
      val user = UserEntity("Jorge Hernandez")
      val token =  AuthenticationService.encodeToken(user, jwtSecret)

      assert(token == expectedToken)
    }
  }

  describe("#decodeToken") {
    it("returns a user") {
      val user = UserEntity("Jorge Hernandez")
      AuthenticationService.decodeToken(expectedToken, jwtSecret).get == user
    }

    it("returns None") {
      AuthenticationService.decodeToken("invalid", jwtSecret) == None
    }
  }
}