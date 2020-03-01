package tests_helpers

import bounded_contexts.share.infrastructure.DoobieConnection
import cats.effect.IO
import doobie.implicits._
import doobie._
import io.circe.Json
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.{HttpRoutes, Request, Status}
import org.http4s.circe._


object TestsHelpers {
  def truncateTable(table: String): Unit = {
    val connection = new DoobieConnection()
    Fragment.const(s"truncate ${table}").update.run.transact(connection.xa).unsafeRunSync()
  }

  def checkRequestAsJson(service: HttpRoutes[IO], request: Request[IO])(callback: (Status, Json) => Unit): Unit = {
    val clientRequest = service.run(request).value.unsafeRunSync().get
    val body = clientRequest.asJson.unsafeRunSync()

    callback(clientRequest.status, body)
  }

}
