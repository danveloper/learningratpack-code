package app

import ratpack.exec.Promise

class DefaultUserService implements UserService {

  @Override
  Promise<User> getUser(String username) {
    Promise.sync {
      new User(username: username, email: "danielpwoods@gmail.com")
    }
  }
}
