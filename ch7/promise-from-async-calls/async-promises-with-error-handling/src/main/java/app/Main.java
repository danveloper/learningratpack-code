package app;

import ratpack.server.RatpackServer;
import java.lang.Exception;

public class Main {

  public static void main(String[] args) throws Exception {
    RatpackServer.start(spec -> spec
      .registryOf(r -> r
        .add(PromiseDatabaseService.class, 
          new PromiseDatabaseService(new DemoAsyncDatabaseService()))
        .add(new UserRenderer())
        .add(new UserProfileRenderer())
      )
      .handlers(chain -> chain
        .get(":username", ctx -> {
          PromiseDatabaseService db = ctx.get(PromiseDatabaseService.class);
          String username = ctx.getPathTokens().get("username");
          db.findByUsername(username).then(user ->
            ctx.render(user)
          );
        })
        .prefix("profile", pchain -> pchain
          .get(":username", ctx -> {
            PromiseDatabaseService db = ctx.get(PromiseDatabaseService.class);
            String username = ctx.getPathTokens().get("username");

            db.findByUsername(username).flatMap(user ->
              db.loadUserProfile(user.getProfileId())
            ).then(profile ->
              ctx.render(profile)
            );
          })
        )
      )
    );
  }
}
