package app;

import java.util.List;

/**
 * This is what the service contract for a non-async, blocking database service might look like.
 * The implementation is represented in {@link DemoBlockingDatabaseService}.
 */
public interface BlockingDatabaseService {
  User findByUsername(String username);
  UserProfile loadUserProfile(Long profileId);
  Friends loadUserFriends(List<Long> friendIds);
  Photos loadPhotosFromFriends(List<Long> photoIds);
}
