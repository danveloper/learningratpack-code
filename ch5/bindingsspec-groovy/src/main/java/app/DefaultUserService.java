package app;

import ratpack.exec.Promise;
import java.util.List;
import java.util.ArrayList;

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
}
