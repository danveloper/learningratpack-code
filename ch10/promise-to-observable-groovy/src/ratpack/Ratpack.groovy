import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

import app.UserService
import app.DefaultUserService
import ratpack.rx.RxRatpack
import ratpack.server.Service
import ratpack.server.StartEvent

ratpack {
  bindings {
    bind(UserService, DefaultUserService)

    bindInstance new Service() {
      @Override
      void onStart(StartEvent e) {
        RxRatpack.initialize()
      }
    }
  }
  handlers {
    prefix("user") {
      get(":username") { UserService userService ->
        userService.getUser(pathTokens.username)
          .observe() // <1>
          .subscribe { user ->
            render json(user)
          }
      }
      get { UserService userService ->
        userService.getUsers()
          .observeEach() // <2>
          .toList()
          .subscribe { users ->
            render json(users)
          }
      }
    }
  }
}
