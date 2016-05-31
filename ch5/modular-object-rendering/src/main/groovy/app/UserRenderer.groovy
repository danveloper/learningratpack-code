package app

import ratpack.handling.Context
import ratpack.render.Renderer

import static groovy.json.JsonOutput.toJson

class UserRenderer implements Renderer<User> {
  @Override
  Class<User> getType() {
    User
  }

  @Override
  void render(Context context, User user) throws Exception { // <1>
    def showAll = context.request.queryParams.containsKey("showAll") && // <2>
        context.request.queryParams.showAll == "true"

    if (showAll) { // <3>
      context.render(toJson(user))
    } else {
      context.render(toJson([username: user.username]))
    }
  }
}
