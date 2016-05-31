package app

import rx.Observable

interface UserService {
  
  Observable<User> getUsers()
}
