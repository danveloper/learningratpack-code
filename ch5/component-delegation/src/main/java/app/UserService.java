package app;

import ratpack.exec.Promise;
import java.util.List;

public interface UserService {

  Promise<List<User>> list();

  Promise<User> getUser(String username);

  Promise<UserProfile> getProfileByToken(String token);
}
