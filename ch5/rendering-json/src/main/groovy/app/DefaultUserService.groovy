package app

import ratpack.exec.Blocking
import ratpack.exec.Operation
import ratpack.exec.Promise

import javax.inject.Inject

class DefaultUserService implements UserService {
  
  private final UserDAO dao

  @Inject
  DefaultUserService(UserDAO dao) {
    this.dao = dao
  }

  @Override
  Promise<List<User>> list() {
    Blocking.get {
      dao.listUsers()
    }
  }

  @Override
  Promise<User> getUser(String username) {
    Blocking.get {
      dao.findByUsername(username)
    }
  }
}
