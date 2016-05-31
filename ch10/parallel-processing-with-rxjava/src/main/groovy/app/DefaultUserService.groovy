package app

import rx.Observable

class DefaultUserService implements UserService {

  def storage = [
    danveloper: new User(id: 1, username: "danveloper", email: "danielpwoods@gmail.com", password: "d13fbf77594580dfcfb9d19a9b6d2327"),
    ldaley: new User(id: 2, username: "ldaley", email: "ld@ldaley.com", password: "d13fbf77594580dfcfb9d19a9b6d2327")
  ]

  Observable<User> getUsers() {
    Observable.from(storage.values())
  }
}
