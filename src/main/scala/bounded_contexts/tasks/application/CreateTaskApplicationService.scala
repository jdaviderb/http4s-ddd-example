package bounded_contexts.tasks.application

import bounded_contexts.share.infrastructure.DoobieConnection
import bounded_contexts.tasks.domain.TaskEntity
import bounded_contexts.tasks.infrestucture.TaskRepository

object CreateTaskApplicationService {
  val connection = new DoobieConnection
  val taskRepository = new TaskRepository(connection)

  def create(task: TaskEntity) = taskRepository.create(task)
}

