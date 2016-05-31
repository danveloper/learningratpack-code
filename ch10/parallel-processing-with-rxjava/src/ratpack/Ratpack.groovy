import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

import ratpack.rx.RxRatpack
import ratpack.server.Service
import ratpack.server.StartEvent
import ratpack.func.Pair
import app.UserService
import app.UserProfileService
import app.DefaultUserService
import app.DefaultUserProfileService

ratpack {
  bindings {
    bind(UserService, DefaultUserService)
    bind(UserProfileService, DefaultUserProfileService)

    bindInstance new Service() {
      @Override
      void onStart(StartEvent e) {
        RxRatpack.initialize()
      }
    }
  }
  handlers {
    get("user") { UserService userService, UserProfileService userProfileService ->
      userService.getUsers()
        .compose(RxRatpack.&forkEach) // <1>
        .flatMap { user -> 
          userProfileService.getUserProfile(user.id).map { userProfile ->
            new Pair(user, userProfile) // <2>
          }
        }
        .map { pair -> // <3>
            def user = pair.left
            def userProfile = pair.right
            [
              username: user.username, 
              email: user.email,
              jobTitle: userProfile.jobTitle,
              phoneNumber: userProfile.phoneNumber
            ]
        }
        .promise() // <4>
        .then { maps ->
          render json(maps)
        }
    }
  }
}
