package app;

import java.util.List;
import ratpack.exec.Promise;

public interface AsyncDatabaseService {
  Promise<User> findByUsername(String username);
  Promise<UserProfile> loadUserProfile(Long profileId);
  Promise<Friends> loadUserFriends(List<Long> friendIds);
  Promise<Photos> loadPhotosFromFriends(List<Long> photoIds);
}
