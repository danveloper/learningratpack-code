package app

import ratpack.exec.Blocking
import ratpack.exec.Operation
import ratpack.exec.Promise

import javax.inject.Inject

class DefaultUserService implements UserService {
  private final UserDAO dao
  private final String nodeName

  DefaultUserService(UserDAO dao, String nodeName) {
    this.dao = dao
    this.nodeName = nodeName
  }

  @Override
  Promise<List<User>> list() {
    println "Querying user list on $nodeName"
    Blocking.get {
      dao.listUsers()
    }
  }
}
