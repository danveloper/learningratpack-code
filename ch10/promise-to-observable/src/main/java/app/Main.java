package app;

import ratpack.exec.Promise;
import ratpack.rx.RxRatpack;
import ratpack.server.RatpackServer;
import java.util.List;

import static ratpack.jackson.Jackson.json;

public class Main {
  
  public static void main(String[] args) throws Exception {
    RxRatpack.initialize();

    RatpackServer.start(spec -> spec
      .registryOf(r -> r
        .add(UserService.class, new DefaultUserService())
      )
      .handlers(chain -> chain
        .prefix("user", pchain -> pchain
          .get(":username", ctx -> {
            UserService userService = ctx.get(UserService.class);
            String username = ctx.getPathTokens().get("username");
            Promise<User> userPromise = userService.getUser(username);
            RxRatpack.observe(userPromise).subscribe(user ->  // <1>
              ctx.render(json(user))
            );
          })
          .get(ctx -> {
            UserService userService = ctx.get(UserService.class);
            Promise<List<User>> usersPromise = userService.getUsers();
            RxRatpack.observeEach(usersPromise).toList().subscribe(users -> // <2>
              ctx.render(json(users))
            ); 
          })
        )
      )
    );
  }
}
