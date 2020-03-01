package bounded_contexts.tasks.application

import bounded_contexts.share.infrastructure.DoobieConnection
import bounded_contexts.tasks.infrestucture.TaskRepository
import bounded_contexts.tasks.domain.TaskEntity
import cats.effect.IO

object FilterTaskApplicationService {
  val connection = new DoobieConnection
  val taskRepository = new TaskRepository(connection)

  def filter: IO[List[TaskEntity]] = taskRepository.all
}
