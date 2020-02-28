package bounded_contexts.tasks.public

import bounded_contexts.share.infrastructure.DoobieConnection
import bounded_contexts.tasks.{TaskEntity, TaskRepository}

object FindTaskApplicationService {
  val connection = new DoobieConnection
  val taskRepository = new TaskRepository(connection)

  def find(id: Int) = taskRepository.find(id)
}
