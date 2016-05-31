package app

import ratpack.handling.Context
import ratpack.handling.Handler

class FooBarFileHandler implements Handler {

  @Override
  void handle(Context ctx) {
      // capture the "?file=<file>" query param
      def fileParam = ctx.request.queryParams.file
      
      // check to make sure it was either "foo" or "bar"
      if (fileParam == "foo" || fileParam == "bar") {
        // if so, then use the Context.file(..) call to read the requested resource
        ctx.render(ctx.file("/html/${fileParam}.html"))
      } else {
        // if not, then set the status to 404 and render back the error page
        ctx.response.status(404)
        ctx.render(ctx.file("/html/error.html"))
      }
  }
}
