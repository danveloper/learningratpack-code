@Grab('io.ratpack:ratpack-groovy:1.3.3')

import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    get("foo/:id?") {
      def name = pathTokens.id ?: "World"
      response.send "Hello $name!"
    }
  }
}
