package app;

import ratpack.exec.Promise;
import java.util.*;

public class DefaultUserService implements UserService {

  private final Map<String, User> storage = new HashMap<String, User>() {{
    User user1 = new User();
    user1.setUsername("danveloper");
    user1.setEmail("danielpwoods@gmail.com");
    put("danveloper", user1);

    User user2 = new User();
    user2.setUsername("ldaley");
    user2.setEmail("ld@ldaley.com");
    put("ldaley", user2);
  }};

  @Override
  public Promise<User> getUser(String username) {
    return Promise.sync(() ->
      storage.containsKey(username) ? storage.get(username) : null
    );
  }

  @Override
  public Promise<List<User>> getUsers() {
    return Promise.sync(() ->
      new ArrayList<User>(storage.values())
    );
  }
}
