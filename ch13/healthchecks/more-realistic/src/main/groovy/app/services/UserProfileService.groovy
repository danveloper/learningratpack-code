package app.services

import app.model.UserProfile
import ratpack.exec.Promise
import ratpack.exec.Operation

interface UserProfileService {

  Promise<UserProfile> getProfile(Long profileId)

  Operation ping()
}
