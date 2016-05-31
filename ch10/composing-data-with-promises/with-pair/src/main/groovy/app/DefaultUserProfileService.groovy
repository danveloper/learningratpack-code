package app

import ratpack.exec.Promise

class DefaultUserProfileService implements UserProfileService {

  @Override
  Promise<UserProfile> getUserProfile(String username) {
    Promise.sync {
      new UserProfile(username: username, jobTitle: "Senior Engineer", phoneNumber: "555-555-5555")
    }
  }

}
