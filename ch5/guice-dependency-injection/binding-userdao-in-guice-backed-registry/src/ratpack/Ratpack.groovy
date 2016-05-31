import app.DefaultUserService
import app.MySqlUserDAO
import app.UserDAO
import app.UserService

import static groovy.json.JsonOutput.toJson
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    bindInstance(UserDAO, new MySqlUserDAO()) // <1>
    bindInstance(UserService, new DefaultUserService())
  }

  handlers {
    all { UserService userService ->
      userService.list().then { users ->
        render(toJson(users))
      }
    }
  }
}
