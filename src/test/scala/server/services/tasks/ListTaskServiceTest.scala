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

class ListTaskServiceTest extends FunSpec {
  val services = Services.all

  describe("/tasks") {
    it("responds 200") {
      TestsHelpers.truncateTable("tasks")

      TestsHelpers.checkRequestAsJson(
        services,
        Request[IO](Method.GET, Uri(path = "/tasks"))
      ) { (status, _) => assert(status == Status.Ok) }
    }

    it("returns an empty list") {
      TestsHelpers.truncateTable("tasks")
      val expectedResponse: List[TaskEntity] = List()

      TestsHelpers.checkRequestAsJson(
        services,
        Request[IO](Method.GET, Uri(path = "/tasks"))
      ) { (status, body) =>
        assert(status == Status.Ok)
        assert(body == expectedResponse.asJson)
      }
    }

    it("returns a list of tasks") {
      TestsHelpers.truncateTable("tasks")
      val task = CreateTaskApplicationService.create(TaskEntity(None, "test", false)).unsafeRunSync()
      val expectedResponse = List(task)

      TestsHelpers.checkRequestAsJson(
        services,
        Request[IO](Method.GET, Uri(path = "/tasks"))
      ) { (status, body) =>
        assert(status == Status.Ok)
        assert(body == expectedResponse.asJson)
      }
    }

  }
}