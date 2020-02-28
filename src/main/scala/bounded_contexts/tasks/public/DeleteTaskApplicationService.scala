package bounded_contexts.tasks.public

import bounded_contexts.share.infrastructure.DoobieConnection
import bounded_contexts.tasks.{TaskEntity, TaskRepository}

object DeleteTaskApplicationService {
  val connection = new DoobieConnection
  val taskRepository = new TaskRepository(connection)

  def delete(id: Int) = taskRepository.delete(id)
}
