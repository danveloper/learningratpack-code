package app

import rx.Observable

interface UserProfileService {
  
  Observable<UserProfile> getUserProfile(Long id)
}
