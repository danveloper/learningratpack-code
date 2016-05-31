package app;

import ratpack.render.Renderer;
import ratpack.handling.Context;
import java.lang.Exception;

import static ratpack.jackson.Jackson.json;

public class PhotosRenderer implements Renderer<Photos> {

  @Override
  public Class<Photos> getType() {
    return Photos.class;
  }

  @Override
  public void render(Context context, Photos photos) throws Exception {
    context.render(json(photos));
  }
}
