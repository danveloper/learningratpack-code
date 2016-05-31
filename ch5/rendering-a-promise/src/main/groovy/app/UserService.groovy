package app

import ratpack.exec.Promise

interface UserService {
  Promise<List<User>> list()
  Promise<User> getUser(String username)
}
