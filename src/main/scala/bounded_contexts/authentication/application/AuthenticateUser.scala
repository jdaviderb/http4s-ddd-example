package bounded_contexts.authentication.application

import bounded_contexts.share.domain.UserEntity
import bounded_contexts.authentication.domain.AuthenticationService

object AuthenticateUser {
  def authenticate(user: UserEntity): String = AuthenticationService.encodeToken(user)
}
