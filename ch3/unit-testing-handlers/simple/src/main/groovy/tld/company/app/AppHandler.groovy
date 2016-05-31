package tld.company.app

import ratpack.handling.Handler
import ratpack.handling.Context

class AppHandler implements Handler {

  @Override
  void handle(Context ctx) {
    ctx.response.send("ok")
  }
}
