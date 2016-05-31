package app;

import rx.Observable;

public interface RxJavaUserService {
  public Observable<User> getUser(String username);
  public Observable<User> getUsers();
}
