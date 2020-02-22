package server.routes

import akka.http.scaladsl.model.HttpMethod
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.RequestContext
import akka.http.scaladsl.model.HttpMethods.GET

abstract class RouteAbstract {
  var queryParams: Option[QueryParams] = None
  var context:  Option[RequestContext] = None
  val route = method(routeMethod) { path(routeUrl)(setup) }

  val routeMethod: HttpMethod
  val routeUrl: String

  def setup(context: RequestContext) = {
    this.context = Some(context)
    this.queryParams = Some(new QueryParams(context.request.uri.toString()))
    this.handler(context)
  }

  def handler(context: RequestContext) = {
    context.complete(NotFound, "handler is not defined")
  }
}
