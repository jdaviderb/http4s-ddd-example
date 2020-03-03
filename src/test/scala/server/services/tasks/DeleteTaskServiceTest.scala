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

class DeleteTaskServiceTest extends FunSpec {
  val services = Services.all
  val authHeader = TestsHelpers.AuthHeaders(UserEntity("Jorge Hernandez"))

  describe("/tasks/:id") {
    it("deletes a task") {
      TestsHelpers.truncateTable("tasks")
      val task = CreateTaskApplicationService.create(TaskEntity(None, "test", false)).unsafeRunSync()
      val request = Request[IO](
        Method.DELETE,
        Uri(path = s"/tasks/${task.id.get}"),
        HttpVersion.`HTTP/1.0`,
        Headers.of(authHeader)
      )

      TestsHelpers.checkRequestAsJson(
        services,
        request
      ) { (status, _) =>
        val taskDeleted = FindTaskApplicationService.find(task.id.get).unsafeRunSync()
        assert(taskDeleted == None)
        assert(status == Status.Ok)
      }
    }
  }
}