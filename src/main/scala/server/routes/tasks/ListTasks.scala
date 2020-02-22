package server.routes.tasks

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.model.HttpMethods.GET
import akka.http.scaladsl.server.RequestContext
import server.routes.{RouteAbstract, RouteContract}

class ListTasks extends RouteAbstract with RouteContract  {
  override val routeUrl  = "status"
  override lazy val routeMethod = GET

  override def handler(context: RequestContext) = {
    context.complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "hello world"))
  }
}