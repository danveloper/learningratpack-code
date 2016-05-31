package app;

import ratpack.server.RatpackServer;
import java.lang.Exception;
import ratpack.exec.Blocking;
import ratpack.exec.Operation;

public class Main {

  private static class ObjectContainer {
    User user;
    UserProfile profile;
    Friends friends;
    Photos photos;
  }

  public static void main(String[] args) throws Exception {
    RatpackServer.start(spec -> spec
      .registryOf(r -> r
        .add(BlockingDatabaseService.class, new DemoBlockingDatabaseService())
        .add(new UserRenderer())
        .add(new UserProfileRenderer())
        .add(new PhotosRenderer())
      )
      .handlers(chain -> chain
        .get(":username", ctx -> {
          BlockingDatabaseService db = ctx.get(BlockingDatabaseService.class);
          String username = ctx.getPathTokens().get("username");
          Blocking.get(() -> {
            return db.findByUsername(username);
          }).then(user -> ctx.render(user));
        })
        .prefix("profile", pchain -> pchain
          .get(":username", ctx -> {
            BlockingDatabaseService db = ctx.get(BlockingDatabaseService.class);
            String username = ctx.getPathTokens().get("username");

            final ObjectContainer container = new ObjectContainer();

            Blocking.get(() -> db.findByUsername(username))
              .then(u1 -> container.user = u1);

            Blocking.get(() -> db.loadUserProfile(container.user.getProfileId()))
              .then(p1 -> container.profile = p1);

            // We can safely do this here without fear of NullPointerException
            // because Ratpack serializes the execution segments, giving us
            // concurrency safety for the value we care about.
            Operation.of(() -> ctx.render(container.profile)).then();
          })
        )
        .prefix("photos", pchain -> pchain
          .get(":username", ctx -> {
            BlockingDatabaseService db = ctx.get(BlockingDatabaseService.class);
            String username = ctx.getPathTokens().get("username");

            final ObjectContainer container = new ObjectContainer();

            Blocking.get(() -> db.findByUsername(username))
              .then(u1 -> container.user = u1);

            Blocking.get(() -> db.loadUserProfile(container.user.getProfileId()))
              .then(p1 -> container.profile = p1);

            Blocking.get(() -> db.loadUserFriends(container.profile.getFriendIds()))
              .then(f1 -> container.friends = f1);

            Blocking.get(() -> db.loadPhotosFromFriends(container.friends.getPhotoIds()))
              .then(p1 -> container.photos = p1);
            
            Operation.of(() -> ctx.render(container.photos)).then();
          })
        )
      )
    );
  }
}
