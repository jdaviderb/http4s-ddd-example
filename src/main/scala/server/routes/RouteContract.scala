package server.routes
import akka.http.scaladsl.server.{StandardRoute, Route}

trait RouteContract {
  val route: Route
}

