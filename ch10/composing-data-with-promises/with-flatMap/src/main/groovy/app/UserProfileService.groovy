package app

import ratpack.exec.Promise

interface UserProfileService {
  
  Promise<UserProfile> getUserProfile(String username)
}
