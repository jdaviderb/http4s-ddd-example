package server.services.tasks

import bounded_contexts.share.domain.UserEntity
import server.services.Services
import org.scalatest._
import org.http4s._
import cats.effect.IO
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import io.circe.literal._
import bounded_contexts.tasks.application.FindTaskApplicationService
import tests_helpers.TestsHelpers
class CreateTaskServiceTest extends FunSpec {
  val services = Services.all
  val authHeader = TestsHelpers.AuthHeaders(UserEntity("Jorge Hernandez"))

  describe("/tasks") {
    it("creates a task") {
      TestsHelpers.truncateTable("tasks")
      val body = json"""{"title":"new", "done": true}"""
      val request =  Request[IO](Method.POST, Uri(path = s"/tasks"), HttpVersion.`HTTP/1.0`, Headers.of(authHeader))
      TestsHelpers.checkRequestAsJson(
        services,
        request.withEntity(body)
      ) { (status, body) =>
        val taskId = body.asObject.get("id").get.toString.toInt
        val task = FindTaskApplicationService.find(taskId).unsafeRunSync().get
        assert(task.title == "new")
        assert(task.done)
        assert(task.asJson == body)
        assert(status == Status.Created)
      }
    }
  }
}