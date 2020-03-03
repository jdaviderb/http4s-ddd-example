package server.services.tasks

import bounded_contexts.share.domain.UserEntity
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
  val authHeader = TestsHelpers.AuthHeaders(UserEntity("Jorge Hernandez"))
  val request = Request[IO](Method.GET, Uri(path = "/tasks"), HttpVersion.`HTTP/1.0`, Headers.of(authHeader))

  describe("/tasks") {
    it("responds 200") {
      TestsHelpers.truncateTable("tasks")

      TestsHelpers.checkRequestAsJson(
        services,
        request
      ) { (status, _) => assert(status == Status.Ok) }
    }

    it("returns an empty list") {
      TestsHelpers.truncateTable("tasks")
      val expectedResponse: List[TaskEntity] = List()

      TestsHelpers.checkRequestAsJson(
        services,
        request
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
        request
      ) { (status, body) =>
        assert(status == Status.Ok)
        assert(body == expectedResponse.asJson)
      }
    }

  }
}