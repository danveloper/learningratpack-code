package app

import ratpack.exec.Promise

interface UserService {
  
  Promise<User> getUser(String username)
}
