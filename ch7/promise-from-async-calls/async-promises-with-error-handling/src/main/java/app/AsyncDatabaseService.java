package app;

import java.util.function.Consumer;

public interface AsyncDatabaseService {

  void findByUsername(String username, 
                      Consumer<User> callback, 
                      Consumer<Throwable> error);

  void loadUserProfile(Long profileId, 
                       Consumer<UserProfile> callback,
                       Consumer<Throwable> error);
}
