package server

import cats.implicits._
import server.services.tasks._
object Services {
  val all =
      new ListTaskService().service <+>
        new HelloService().service
}
