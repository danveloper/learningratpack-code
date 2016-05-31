package app

import org.reactivestreams.Publisher

interface UserService {
  
  Publisher<User> getUsers() // <1>
}
