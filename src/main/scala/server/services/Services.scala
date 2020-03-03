package server.services

import tasks._
import authentication._
import cats.implicits._

object Services {
  val all = new ListTaskService().service <+>
    new CreateTaskService().service <+>
    new GetTaskService().service <+>
    new ListTaskService().service <+>
    new DeleteTaskService().service <+>
    new UpdateTaskService().service <+>
    new AuthenticateService().service
}
