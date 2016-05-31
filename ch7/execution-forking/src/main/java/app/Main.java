package app;

import ratpack.guice.Guice;
import ratpack.server.RatpackServer;
import ratpack.test.embed.EmbeddedApp;

import java.util.ArrayList;
import java.util.List;

import static ratpack.jackson.Jackson.json;

public class Main {
  private static final List<User> demoUsers = new ArrayList<User>() {{
    User user1 = new User();
    user1.setUsername("danveloper");
    user1.setEmail("danielpwoods@gmail.com");
    User user2 = new User();
    user2.setUsername("ratpack");
    user2.setEmail("no-reply@ratpack.io");
    add(user1);
    add(user2);
  }};

  private static void standupRemoteService() throws Exception {
    EmbeddedApp remoteDemoService = EmbeddedApp.of(spec -> spec
        .handlers(chain -> chain
            .get("users", ctx -> {
              ctx.render(json(demoUsers));
            })
        )
    );

    String remoteUri = remoteDemoService.getAddress().toString();
    UserService.USER_SERVICE_URI = remoteUri.substring(0, remoteUri.length()-1);
  }

  public static void main(String[] args) throws Exception {
    standupRemoteService();

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
