package app.services

import app.model.User
import ratpack.exec.Promise
import ratpack.exec.Operation

interface UserService {

  Promise<User> getById(Long id)

  Promise<User> getByUsername(String username)

  Promise<List<User>> listUsers()

  Operation save(User user)

  Operation ping()
}
