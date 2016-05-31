import app.DefaultUserService
import app.UserService

import static groovy.json.JsonOutput.toJson
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    bindInstance(UserService, new DefaultUserService()) // <1>
  }  
  
  handlers {
    all { UserService userService -> // <2>
      userService.list().then { users ->
        render(toJson(users))
      }
    }
  }
}
