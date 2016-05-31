package app;

import ratpack.exec.Promise;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class DefaultUserService implements UserService {

  private final List<User> demoUsers = new ArrayList<User>() {{
    User user1 = new User();
    user1.setUsername("danveloper");
    user1.setEmail("danielpwoods@gmail.com");
    add(user1);

    User user2 = new User();
    user2.setUsername("ldaley");
    user2.setEmail("ld@ldaley.com");
    add(user2);
  }};

  @Override
  public Promise<List<User>> list() {
    return Promise.sync(() -> demoUsers);
  } 

  @Override
  public Promise<User> getUser(String username) {
    User user = null;
    if ("danveloper".equals(username)) {
      user = demoUsers.get(0);
    } else if ("ldaley".equals(username)) {
      user = demoUsers.get(1);
    }
    final User responseUser = user;
    return Promise.sync(() -> responseUser);
  }

  @Override
  public Promise<UserProfile> getProfileByToken(String token) {
    UserProfile profile = new UserProfile();
    if ("0123ratpack456".equals(token)) {
      profile.setUsername("ratpack");
      profile.setRoles(Arrays.asList("showuser", "listuser", "admin"));
    } else {
      profile.setUsername("guest");
      profile.setRoles(Arrays.asList("guest"));
    }
    return Promise.sync(() -> profile);
  }
}
