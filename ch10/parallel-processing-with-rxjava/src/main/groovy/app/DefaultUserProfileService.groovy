package app

import rx.Observable

class DefaultUserProfileService implements UserProfileService {

  def storage = [
    new UserProfile(username: "danveloper", jobTitle: "Software Developer", phoneNumber: "555-555-5555"),
    new UserProfile(username: "ldaley", jobTitle: "Senior Architect", phoneNumber: "+61 5555 5555")
  ]

  Observable<UserProfile> getUserProfile(Long id) {
    Observable.just(storage.getAt( (id as int) - 1))
  }
}
