package app;

import java.util.List;
import java.util.function.Consumer;

public interface AsyncDatabaseService {
  void findByUsername(String username, Consumer<User> callback);
  void loadUserProfile(Long profileId, Consumer<UserProfile> callback);
  void loadUserFriends(List<Long> friendIds, Consumer<Friends> callback);
  void loadPhotosFromFriends(List<Long> photoIds, Consumer<Photos> callback);
}
