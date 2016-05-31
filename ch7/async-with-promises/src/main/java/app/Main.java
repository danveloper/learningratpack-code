package app;

import ratpack.server.RatpackServer;
import java.lang.Exception;

public class Main {

  public static void main(String[] args) throws Exception {
    RatpackServer.start(spec -> spec
      .registryOf(r -> r
        .add(AsyncDatabaseService.class, new DemoAsyncDatabaseService())
        .add(new UserRenderer())
        .add(new UserProfileRenderer())
        .add(new PhotosRenderer())
      )
      .handlers(chain -> chain
        // No so bad with callbacks, but not ideal
        .get(":username", ctx -> {
          AsyncDatabaseService db = ctx.get(AsyncDatabaseService.class);
          String username = ctx.getPathTokens().get("username");
          db.findByUsername(username).then(user -> ctx.render(user));
        })
        // Still not so bad, but complexity is growing
        .prefix("profile", pchain -> pchain
          .get(":username", ctx -> {
            AsyncDatabaseService db = ctx.get(AsyncDatabaseService.class);
            String username = ctx.getPathTokens().get("username");

            db.findByUsername(username).flatMap(user -> { 
              return db.loadUserProfile(user.getProfileId());
            }).then(profile -> { 
              ctx.render(profile);
            });
          })
        )
        // Enter callback hell.
        .prefix("photos", pchain -> pchain
          .get(":username", ctx -> {
            AsyncDatabaseService db = ctx.get(AsyncDatabaseService.class);
            String username = ctx.getPathTokens().get("username");

            db.findByUsername(username).flatMap(user -> { 
              return db.loadUserProfile(user.getProfileId());
            }).flatMap(profile -> {
              return db.loadUserFriends(profile.getFriendIds());
            }).flatMap(friends -> {
              return db.loadPhotosFromFriends(friends.getPhotoIds());
            }).then(photos -> {
              ctx.render(photos);
            });
          })
        )
      )
    );
  }
}
