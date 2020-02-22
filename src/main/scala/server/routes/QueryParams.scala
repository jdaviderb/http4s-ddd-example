package server.routes

import io.lemonlabs.uri.Url

class QueryParams(val query: String) {
  val url = Url.parse(query)

  def param(key: String) = url.query.paramMap.get(key)
}
