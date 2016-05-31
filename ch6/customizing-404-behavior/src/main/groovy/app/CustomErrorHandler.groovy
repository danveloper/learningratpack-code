package app

import ratpack.error.ClientErrorHandler
import ratpack.handling.Context

class CustomErrorHandler implements ClientErrorHandler {
  @Override
  void error(Context ctx, int statusCode) throws Exception {
    ctx.render(ctx.file("static/404.html"))
  }
}
