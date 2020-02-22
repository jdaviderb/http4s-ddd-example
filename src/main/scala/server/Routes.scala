package server

import server.routes.tasks.ListTasks

object Routes {
  val all =
      new ListTasks().route
}
