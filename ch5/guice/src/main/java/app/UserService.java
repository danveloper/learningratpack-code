package app;

import ratpack.exec.Promise;
import java.util.List;

public interface UserService {

  Promise<List<User>> list();
}
