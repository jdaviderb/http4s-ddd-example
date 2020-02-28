package bounded_contexts.tasks

case class TaskEntity(id: Option[Int], title: String, done: Boolean)
