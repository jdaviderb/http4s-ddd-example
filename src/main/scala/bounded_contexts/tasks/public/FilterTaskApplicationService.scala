package bounded_contexts.tasks.public

import bounded_contexts.share.infrastructure.DoobieConnection
import bounded_contexts.tasks.TaskRepository

object FilterTaskApplicationService {
  val connection = new DoobieConnection
  val taskRepository = new TaskRepository(connection)

  def filter = taskRepository.all
}
