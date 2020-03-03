package server.services

import tasks._
import authentication._
import cats.implicits._
import middleware.AuthMiddleware

object Services {
  val authRoutes = AuthMiddleware.to(
    new ListTaskService().service <+>
      new CreateTaskService().service <+>
      new GetTaskService().service <+>
      new ListTaskService().service <+>
      new DeleteTaskService().service <+>
      new UpdateTaskService().service
  )
  val publicRoutes = new AuthenticateService().service
  val all = authRoutes <+> publicRoutes
}
