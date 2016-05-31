package app;

import java.util.function.Consumer;
import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import ratpack.exec.Promise;

public class DemoAsyncDatabaseService implements AsyncDatabaseService {

  @Override
  public Promise<User> findByUsername(String username) {
    return Promise.async(down -> {
      new Thread(() -> {
        try {
          // simulate a db call
          Thread.sleep(500);
          // demo data
          User user = new User();
          user.setId(1l);
          user.setUsername(username);
          user.setProfileId(1l);
          down.success(user);
        } catch (Exception e) {
          down.error(e);
        }
      }).start();
    });
  }

  @Override
  public Promise<UserProfile> loadUserProfile(Long profileId) {
    return Promise.async(down -> {
      new Thread(() -> {
        try {
          // simulate a db call
          Thread.sleep(200);

          UserProfile profile = new UserProfile();
          profile.setId(profileId);
          profile.setUserId(1l);
          profile.setFirstName("Edgar");
          profile.setMiddleName("Allan");
          profile.setLastName("Poe");
          profile.setFriendIds(Arrays.asList(2l, 3l, 4l));
          down.success(profile);
        } catch (Exception e) {
          down.error(e);
        }
      }).start();
    });
  }

  @Override
  public Promise<Friends> loadUserFriends(List<Long> friendIds) {
    return Promise.async(down -> {
      new Thread(() -> {
        try {
          // simulate a db call
          Thread.sleep(200);

          List<Friends.FriendReference> references = new ArrayList<>();
          Long currentPhotoId = 1l;
          for (Long friendId : friendIds) {
            List<Long> photoIds = new ArrayList<>();
            for (long i=0;i<2;i++) {
              photoIds.add(++currentPhotoId);
            }
            Friends.FriendReference friend = new Friends.FriendReference(friendId, photoIds);
            references.add(friend);
          }

          Friends friends = new Friends();
          friends.setReferences(references);
          down.success(friends);
        } catch (Exception e) {
          down.error(e);
        }
      }).start();
    });
  }

  @Override
  public Promise<Photos> loadPhotosFromFriends(List<Long> photoIds) {
    return Promise.async(down -> {
      new Thread(() -> {
        try {
          // simulate db call
          Thread.sleep(200);

          List<String> links = new ArrayList<>();
          for (Long photoId : photoIds) {
            String ref = BigInteger.valueOf(100000l + photoId).toString(32);
            links.add("https://example.com/photos/"+ref);
          }
          Photos photos = new Photos();
          photos.setLinks(links);
          down.success(photos);
        } catch (Exception e) {
          down.error(e);
        }
      }).start();
    });
  }
}
