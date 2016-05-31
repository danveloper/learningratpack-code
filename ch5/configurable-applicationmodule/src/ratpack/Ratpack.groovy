import app.ApplicationModule
import app.UserService

import static ratpack.groovy.Groovy.ratpack
import static groovy.json.JsonOutput.toJson

ratpack {
  serverConfig {
    env() // <1>
  }
  bindings {
    moduleConfig( // <2>
      ApplicationModule, 
      serverConfig.get("/user", ApplicationModule.Config)
    )
  }
  handlers {
    get("users") { UserService userService -> // <3>
      userService.list().then { users ->
        render toJson(users)
      }
    }
  }
}
