package bounded_contexts.tasks.application

import bounded_contexts.share.infrastructure.DoobieConnection
import bounded_contexts.tasks.infrestucture.TaskRepository
import bounded_contexts.tasks.domain.TaskEntity
import cats.effect.IO

object FindTaskApplicationService {
  val connection = new DoobieConnection
  val taskRepository = new TaskRepository(connection)

  def find(id: Int): IO[Option[TaskEntity]] = taskRepository.find(id)
}
