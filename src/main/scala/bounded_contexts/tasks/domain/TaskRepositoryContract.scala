package bounded_contexts.tasks.domain

trait TaskRepositoryContract[P[_]] {
  def find(id: Int): P[Option[TaskEntity]]
  def update(task: TaskEntity): P[TaskEntity]
  def create(task: TaskEntity): P[TaskEntity]
  def delete(id: Int): P[Unit]
  def all: P[List[TaskEntity]]
}
