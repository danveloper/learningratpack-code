import app.ApplicationModule
import app.UserService

import static groovy.json.JsonOutput.toJson
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    module(ApplicationModule) // <1>
  }

  handlers {
    all { UserService userService -> // <2>
      userService.list().then { users ->
        render(toJson(users))
      }
    }
  }
}
