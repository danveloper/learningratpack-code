package app;

import ratpack.exec.Promise;

public class PromiseDatabaseService {

  private final AsyncDatabaseService db;

  public PromiseDatabaseService(AsyncDatabaseService db) {
    this.db = db;
  }

  public Promise<User> findByUsername(String username) {
    return Promise.async(down ->
      db.findByUsername(username, user -> 
        down.success(user)
      )
    );
  }

  public Promise<UserProfile> loadUserProfile(Long profileId) {
    return Promise.async(down -> 
      db.loadUserProfile(profileId, profile ->
        down.success(profile)
      )
    );
  }
}
