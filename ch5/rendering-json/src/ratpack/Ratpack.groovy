import app.DefaultUserService
import app.MySqlUserDAO
import app.UserDAO
import app.UserService

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

ratpack {
  bindings {
    bind(UserDAO, MySqlUserDAO)
    bind(UserService, DefaultUserService)
  }

  handlers {
    prefix("users") {
      get(":username") { UserService userService ->
        userService.getUser(pathTokens.username).then { user ->
          render(json(user)) // <1>
        }
      }
    }
  }
}
