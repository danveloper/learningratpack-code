import app.DefaultUserService
import app.MySqlUserDAO
import app.UserDAO
import app.UserService

import static groovy.json.JsonOutput.toJson
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    bindInstance(UserDAO, new MySqlUserDAO())
    bind(UserService, DefaultUserService) // <1>
  }

  handlers {
    all { UserService userService ->
      userService.list().then { users ->
        render(toJson(users))
      }
    }
  }
}
