import app.DefaultUserService
import app.MySqlUserDAO
import app.UserDAO
import app.UserService
import com.google.inject.Scopes

import static groovy.json.JsonOutput.toJson
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    binder { b -> // <1>
      b.bind(UserDAO).to(MySqlUserDAO).in(Scopes.SINGLETON)
      b.bind(UserService).to(DefaultUserService).asEagerSingleton()
    }
  }

  handlers {
    all { UserService userService ->
      userService.list().then { users ->
        render(toJson(users))
      }
    }
  }
}
