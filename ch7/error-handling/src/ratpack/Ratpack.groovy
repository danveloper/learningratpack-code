import ratpack.error.ServerErrorHandler
import ratpack.handling.Context

import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    bindInstance ServerErrorHandler, new ServerErrorHandler() { // <1>
      @Override
      void error(Context ctx, Throwable throwable) throws Exception { // <2>
        ctx.response.status(500)
        ctx.render(ctx.file("errors/500.html"))
      }
    }
  }
  handlers {
    get {
      throw new RuntimeException() // <3>
    }
  }
}
