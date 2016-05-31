package app;

import ratpack.guice.Guice;
import ratpack.server.RatpackServer;

import static ratpack.jackson.Jackson.json;

public class Main {
  public static void main(String[] args) throws Exception {
    RatpackServer.start(spec -> spec
      .registry(Guice.registry(b -> b
        .binder(binder -> binder
          .bind(UserService.class).asEagerSingleton()
        )
      ))
      .handlers(chain -> chain
        .prefix("users", pchain -> pchain
          .get(":username", ctx -> {
            UserService userService = ctx.get(UserService.class);
            String username = ctx.getPathTokens().get("username");

            User user = userService.findByUsername(username);
            ctx.render(json(user));
          })
          .get(ctx -> {
            UserService userService = ctx.get(UserService.class);
            ctx.render(json(userService.list()));
          })
        )
      )
    );
  }
}
