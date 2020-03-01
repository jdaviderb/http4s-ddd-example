package bounded_contexts.tasks.application

import bounded_contexts.share.infrastructure.DoobieConnection
import bounded_contexts.tasks.infrestucture.TaskRepository
import cats.effect.IO

object DeleteTaskApplicationService {
  val connection = new DoobieConnection
  val taskRepository = new TaskRepository(connection)

  def delete(id: Int): IO[Unit] = taskRepository.delete(id)
}
