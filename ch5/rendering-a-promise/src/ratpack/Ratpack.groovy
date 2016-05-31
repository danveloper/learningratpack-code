import app.*

import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    bind(UserDAO, MySqlUserDAO)
    bind(UserService, DefaultUserService)
    bind(UserRenderer)
  }

  handlers {
    prefix("users") {
      get(":username") { UserService userService ->
        render(userService.getUser(pathTokens.username)) // <1>
      }
    }
  }
}
