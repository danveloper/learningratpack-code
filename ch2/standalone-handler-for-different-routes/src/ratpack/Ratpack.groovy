import static ratpack.groovy.Groovy.ratpack
import ratpack.handling.Handler
import ratpack.handling.Context

class DefaultRouteHandler implements Handler {
  private final String defaultMessage

  DefaultRouteHandler(String message) {
    this.defaultMessage = message
  }

  @Override
  void handle(Context context) {
    if (context.pathTokens.containsKey("name")) {
      context.render "Hello, ${context.pathTokens.name}!"
    } else {
      context.render defaultMessage
    }
  }
}

ratpack {
  bindings {
    add(new DefaultRouteHandler("Hello, World!"))
  }
  handlers {
    get(DefaultRouteHandler)
    get(":name", DefaultRouteHandler)
  }
}
