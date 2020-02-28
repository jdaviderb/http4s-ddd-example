package bounded_contexts.tasks.public

import bounded_contexts.share.infrastructure.DoobieConnection
import bounded_contexts.tasks.{TaskEntity, TaskRepository}

object UpdateTaskApplicationService {
  val connection = new DoobieConnection
  val taskRepository = new TaskRepository(connection)

  def update(task: TaskEntity) = taskRepository.update(task)
}

