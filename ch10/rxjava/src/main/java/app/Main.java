package app;

import ratpack.server.RatpackServer;
import ratpack.rx.RxRatpack;
import rx.Observable;

import static ratpack.jackson.Jackson.json;

public class Main {
  
  public static void main(String[] args) throws Exception {
    RxRatpack.initialize(); // <1>

    RatpackServer.start(spec -> spec
      .registryOf(r -> r
        .add(RxJavaUserService.class, new DefaultRxJavaUserService()) // <2>
      )
      .handlers(chain -> chain
        .prefix("user", pchain -> pchain
          .get(":username", ctx -> {
            RxJavaUserService userService = ctx.get(RxJavaUserService.class);
            String username = ctx.getPathTokens().get("username");
            Observable<User> userObs = userService.getUser(username); // <3>
            RxRatpack.promiseSingle(userObs).then(user -> // <4>
              ctx.render(json(user))
            );
          })
          .get(ctx -> {
            RxJavaUserService userService = ctx.get(RxJavaUserService.class);
            Observable<User> usersObs = userService.getUsers(); // <5>
            RxRatpack.promise(usersObs).then(users -> // <6>
              ctx.render(json(users))
            );
          })
        )
      )
    );
  }
}
