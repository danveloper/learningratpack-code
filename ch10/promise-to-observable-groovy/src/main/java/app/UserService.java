package app;

import ratpack.exec.Promise;
import java.util.List;

public interface UserService {

  Promise<User> getUser(String username);

  Promise<List<User>> getUsers();
}
