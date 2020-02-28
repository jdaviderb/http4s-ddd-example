package server

import cats.implicits._
import server.services.tasks._
object Services {
  val all = new ListTaskService().service <+>
        new CreateTaskService().service <+>
        new GetTaskService().service <+>
        new ListTaskService().service <+>
        new DeleteTaskService().service <+>
        new UpdateTaskService().service

}
