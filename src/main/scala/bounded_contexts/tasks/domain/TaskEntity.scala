package bounded_contexts.tasks.domain

case class TaskEntity(id: Option[Int], title: String, done: Boolean)
