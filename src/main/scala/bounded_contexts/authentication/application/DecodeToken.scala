package bounded_contexts.authentication.application

import bounded_contexts.share.domain.UserEntity
import bounded_contexts.authentication.domain.AuthenticationService

object DecodeToken {
  def decode(token: String): Option[UserEntity] = AuthenticationService.decodeToken(token)
}

