import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

import ratpack.service.Service
import ratpack.service.StartEvent
import ratpack.rx.RxRatpack
import app.RxJavaUserService
import app.DefaultRxJavaUserService
import rx.Observable

ratpack {
  bindings {
    bind(RxJavaUserService, DefaultRxJavaUserService)

    bindInstance new Service() {
      @Override
      void onStart(StartEvent e) throws Exception {
        RxRatpack.initialize()
      }
    }
  }
  handlers {
    prefix("user") {
      get(":username") { RxJavaUserService userService ->
        userService.getUser(pathTokens.username)
          .promiseSingle() // <1>
          .then { user ->
            render json(user)
          }
      }
      get { RxJavaUserService userService ->
        userService.getUsers()
          .promise() // <2>
          .then { users ->
            render json(users)
          }
      }
    }
  }
}
