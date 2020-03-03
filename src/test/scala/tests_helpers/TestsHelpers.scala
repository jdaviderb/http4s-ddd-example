package tests_helpers

import bounded_contexts.share.domain.UserEntity
import bounded_contexts.share.infrastructure.DoobieConnection
import cats.effect.IO
import doobie.implicits._
import doobie._
import io.circe.Json
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.{Header, HttpRoutes, Request, Status}
import org.http4s.circe._
import io.circe.literal._
import bounded_contexts.authentication.application.AuthenticateUser

object TestsHelpers {
  def truncateTable(table: String): Unit = {
    val connection = new DoobieConnection()
    Fragment.const(s"truncate ${table}").update.run.transact(connection.xa).unsafeRunSync()
  }

  def AuthHeaders(user: UserEntity) =
      Header("Authorization", s"Bearer ${AuthenticateUser.authenticate(user)}")

  def checkRequestAsJson(service: HttpRoutes[IO], request: Request[IO])(callback: (Status, Json) => Unit): Unit = {
    val clientRequest = service.run(request).value.unsafeRunSync().get
    val isEmpty = clientRequest.body.compile.toVector.unsafeRunSync.isEmpty
    val emptyJson = json"""{}"""
    val body = if (isEmpty) emptyJson else clientRequest.asJson.unsafeRunSync()

    callback(clientRequest.status, body)
  }

}
