package app

import ratpack.exec.Blocking
import ratpack.exec.Operation
import ratpack.exec.Promise

import javax.inject.Inject

class DefaultUserService implements UserService { // <1>

  @Inject // <2>
  UserDAO dao

  @Override
  Promise<List<User>> list() {
    Blocking.get {
      dao.listUsers() // <3>
    }
  }
}
