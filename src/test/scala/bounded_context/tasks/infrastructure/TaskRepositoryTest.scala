package bounded_context.tasks.infrastructure

import bounded_contexts.share.infrastructure.DoobieConnection
import bounded_contexts.tasks.domain.TaskEntity
import bounded_contexts.tasks.infrestucture.TaskRepository
import org.scalatest._
import tests_helpers.TestsHelpers

class TaskRepositoryTest extends FunSpec {
  val connection = new DoobieConnection()
  val repository = new TaskRepository(connection)

  describe("#find") {
    it("returns a task") {
      TestsHelpers.truncateTable("tasks")
      val task = TaskEntity(id = None, title= "test", done=true)
      val newTask = repository.create(task).unsafeRunSync()

      assert(repository.find(newTask.id.get).unsafeRunSync().get == newTask)
    }
  }

  describe("#update") {
    it("updates a task") {
      TestsHelpers.truncateTable("tasks")
      val task = TaskEntity(id = None, title= "test", done=true)
      val newTask = repository.create(task).unsafeRunSync()
      val taskFinder = repository.find(newTask.id.get)

      assert(taskFinder.unsafeRunSync().get.title == "test")
      repository.update(newTask.copy(title = "task updated")).unsafeRunSync()
      assert(taskFinder.unsafeRunSync().get.title == "task updated")
    }
  }

  describe("#delete") {
    it("delete a task") {
      TestsHelpers.truncateTable("tasks")
      val task = TaskEntity(id = None, title= "test", done=true)
      val newTask = repository.create(task).unsafeRunSync()
      val taskFinder = repository.find(newTask.id.get)

      assert(taskFinder.unsafeRunSync() != None)
      repository.delete(newTask.id.get).unsafeRunSync()
      assert(taskFinder.unsafeRunSync() == None)

    }
  }

  describe("#create") {
    it("creates a task") {
      TestsHelpers.truncateTable("tasks")

      assert(repository.all.unsafeRunSync().length == 0)
      val task = TaskEntity(id = None, title= "test", done=true)
      val newTask = repository.create(task).unsafeRunSync()

      assert(newTask.id != None)
      assert(newTask.title == "test")
      assert(newTask.done == true)
      assert(repository.all.unsafeRunSync().length == 1)
      assert(repository.find(newTask.id.get).unsafeRunSync().get == newTask)
    }
  }
}