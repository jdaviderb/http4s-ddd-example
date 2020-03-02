package server.services.tasks

import org.scalatest._
import org.http4s._
import cats.effect.IO
import bounded_contexts.tasks.domain.TaskEntity
import bounded_contexts.tasks.application.CreateTaskApplicationService
import bounded_contexts.tasks.application.FindTaskApplicationService

import tests_helpers.TestsHelpers

class DeleteTaskServiceTest extends FunSpec {
  val deleteTaskService = new DeleteTaskService().service

  describe("/tasks/:id") {
    it("deletes a task") {
      TestsHelpers.truncateTable("tasks")
      val task = CreateTaskApplicationService.create(TaskEntity(None, "test", false)).unsafeRunSync()

      TestsHelpers.checkRequestAsJson(
        deleteTaskService,
        Request[IO](Method.DELETE, Uri(path = s"/tasks/${task.id.get}"))
      ) { (status, _) =>
        val taskDeleted = FindTaskApplicationService.find(task.id.get).unsafeRunSync()
        assert(taskDeleted == None)
        assert(status == Status.Ok)
      }
    }
  }
}