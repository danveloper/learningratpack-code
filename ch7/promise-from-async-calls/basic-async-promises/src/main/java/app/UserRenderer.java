package app;

import ratpack.render.Renderer;
import ratpack.handling.Context;
import java.lang.Exception;

import static ratpack.jackson.Jackson.json;

public class UserRenderer implements Renderer<User> {

  @Override
  public Class<User> getType() {
    return User.class;
  }

  @Override
  public void render(Context context, User user) throws Exception {
    context.render(json(user));
  }
}
