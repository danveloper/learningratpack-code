package app;

import ratpack.render.Renderer;
import ratpack.handling.Context;
import java.lang.Exception;

import static ratpack.jackson.Jackson.json;

public class UserProfileRenderer implements Renderer<UserProfile> {

  @Override
  public Class<UserProfile> getType() {
    return UserProfile.class;
  }

  @Override
  public void render(Context context, UserProfile profile) throws Exception {
    context.render(json(profile));
  }
}
