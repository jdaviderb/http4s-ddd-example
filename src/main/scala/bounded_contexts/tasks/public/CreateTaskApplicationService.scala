package bounded_contexts.tasks.public

import bounded_contexts.share.infrastructure.DoobieConnection
import bounded_contexts.tasks.{TaskEntity, TaskRepository}

object CreateTaskApplicationService {
  val connection = new DoobieConnection
  val taskRepository = new TaskRepository(connection)

  def create(task: TaskEntity) = taskRepository.create(task)
}

