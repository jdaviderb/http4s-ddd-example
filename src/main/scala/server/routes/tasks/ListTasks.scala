package server.routes.tasks

import akka.http.scaladsl.model.HttpMethods.GET
import akka.http.scaladsl.server.RequestContext
import server.routes.{RouteAbstract, RouteContract}
import modules.database.DoobieConnection
import modules.tasks.TaskRepository
import modules.tasks.Task
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport


trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val taskFormat = jsonFormat2(Task)
}

class ListTasks extends RouteAbstract with JsonSupport  {
  override val routeUrl  = "status"
  override lazy val routeMethod = GET

  override def handler(context: RequestContext) = {
    val ok = new DoobieConnection()
    val repository = new TaskRepository(ok)

    val result = for {
      task <- repository.task
    } yield context.complete(task)

    result.unsafeRunSync()
  }
}