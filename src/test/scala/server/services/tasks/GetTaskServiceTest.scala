package server.services.tasks

import server.services.Services
import org.scalatest._
import org.http4s._
import cats.effect.IO
import io.circe.syntax._
import io.circe.generic.auto._
import bounded_contexts.tasks.domain.TaskEntity
import bounded_contexts.tasks.application.CreateTaskApplicationService
import tests_helpers.TestsHelpers

class GetTaskServiceTest extends FunSpec {
  val services = Services.all

  describe("/tasks/:id") {
    it("responds 404") {
      TestsHelpers.truncateTable("tasks")

      TestsHelpers.checkRequestAsJson(
        services,
        Request[IO](Method.GET, Uri(path = "/tasks/0"))
      ) { (status, _) => assert(status == Status.NotFound) }
    }

    it("responds 200 and returns a task") {
      TestsHelpers.truncateTable("tasks")
      val task = CreateTaskApplicationService.create(TaskEntity(None, "test", false)).unsafeRunSync()

      TestsHelpers.checkRequestAsJson(
        services,
        Request[IO](Method.GET, Uri(path = s"/tasks/${task.id.get}"))
      ) { (status, body) =>
        assert(status == Status.Ok)
        assert(body == task.asJson)
      }
    }
  }
}