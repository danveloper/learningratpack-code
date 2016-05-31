import app.DefaultUserService
import app.MySqlUserDAO
import app.UserDAO
import app.UserRenderer
import app.UserService

import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    bind(UserDAO, MySqlUserDAO)
    bind(UserService, DefaultUserService)
    bind(UserRenderer) // <1>
  }

  handlers {
    prefix("users") {
      get(":username") { UserService userService ->
        userService.getUser(pathTokens.username).then { user ->
          render(user) // <2>
        }
      }
    }
  }
}
