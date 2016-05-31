import static ratpack.groovy.Groovy.ratpack
import ratpack.handling.Handler
import ratpack.handling.Context

class DefaultRouteHandler implements Handler {
  private final String message

  DefaultRouteHandler(String message) {
    this.message = message
  }

  @Override
  void handle(Context context) {
    context.render message
  }
}

ratpack {
  bindings {
    add(new DefaultRouteHandler("Hello, World!"))
  }
  handlers {
    get(DefaultRouteHandler)
  }
}
