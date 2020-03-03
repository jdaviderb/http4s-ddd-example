package server.services.tasks

import bounded_contexts.share.domain.UserEntity
import server.services.Services
import org.scalatest._
import org.http4s._
import cats.effect.IO
import bounded_contexts.tasks.domain.TaskEntity
import bounded_contexts.tasks.application.CreateTaskApplicationService
import bounded_contexts.tasks.application.FindTaskApplicationService
import tests_helpers.TestsHelpers
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import io.circe.literal._

class UpdateTaskServiceTest extends FunSpec {
  val services = Services.all
  val authHeader = TestsHelpers.AuthHeaders(UserEntity("Jorge Hernandez"))

  describe("/tasks/:id") {
    it("responds 404") {
      TestsHelpers.checkRequestAsJson(
        services,
        Request[IO](Method.PUT, Uri(path = "/tasks/0"), HttpVersion.`HTTP/1.0`, Headers.of(authHeader))
      ) { (status, _) => assert(status == Status.NotFound) }
    }

    it("updates a task") {
      TestsHelpers.truncateTable("tasks")
      val task = CreateTaskApplicationService.create(TaskEntity(None, "test", false)).unsafeRunSync()
      val body = json"""{"title":"updated", "done": true}"""
      val request = Request[IO](
        Method.PUT,
        Uri(path = s"/tasks/${task.id.get}"),
        HttpVersion.`HTTP/1.0`,
        Headers.of(authHeader)
      )

      TestsHelpers.checkRequestAsJson(
        services,
        request.withEntity(body)
      ) { (status, body) =>
        val updatedTask = FindTaskApplicationService.find(task.id.get).unsafeRunSync().get
        assert(updatedTask.title == "updated")
        assert(updatedTask.done)
        assert(status == Status.Ok)
        assert(body == updatedTask.asJson)
      }
    }
  }
}