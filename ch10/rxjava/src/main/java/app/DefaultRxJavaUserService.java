package app;

import rx.Observable;
import java.util.Map;
import java.util.HashMap;

public class DefaultRxJavaUserService implements RxJavaUserService {

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
  public Observable<User> getUser(String username) {
    return Observable.just(storage.containsKey(username) ? storage.get(username) : null);
  }

  @Override
  public Observable<User> getUsers() {
    return Observable.<User>from(storage.values());
  }
}
