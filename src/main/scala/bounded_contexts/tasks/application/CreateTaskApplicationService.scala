package bounded_contexts.tasks.application

import bounded_contexts.share.infrastructure.DoobieConnection
import bounded_contexts.tasks.domain.TaskEntity
import bounded_contexts.tasks.infrestucture.TaskRepository
import cats.effect.IO

object CreateTaskApplicationService {
  val connection = new DoobieConnection
  val taskRepository = new TaskRepository(connection)

  def create(task: TaskEntity): IO[TaskEntity] = taskRepository.create(task)
}
