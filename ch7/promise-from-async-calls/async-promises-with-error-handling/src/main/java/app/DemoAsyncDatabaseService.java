package app;

import java.util.function.Consumer;
import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class DemoAsyncDatabaseService implements AsyncDatabaseService {

  @Override
  public void findByUsername(String username, Consumer<User> callback, Consumer<Throwable> error) {
    new Thread(() -> {
      try {
        // simulate a db call
        Thread.sleep(500);
        // demo data
        User user = new User();
        user.setId(1l);
        user.setUsername(username);
        user.setProfileId(1l);
        callback.accept(user);
      } catch (Exception e) {
        error.accept(e);
      }
    }).start();
  }

  @Override
  public void loadUserProfile(Long profileId, Consumer<UserProfile> callback, Consumer<Throwable> error) {
    new Thread(() -> {
      try {
        // simulate a db call
        Thread.sleep(200);

        UserProfile profile = new UserProfile();
        profile.setId(profileId);
        profile.setUserId(1l);
        profile.setFirstName("Edgar");
        profile.setMiddleName("Allan");
        profile.setLastName("Poe");
        profile.setFriendIds(Arrays.asList(2l, 3l, 4l));
        callback.accept(profile);
      } catch (Exception e) {
        error.accept(e);
      }
    }).start();
  }
}
