import app.*

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

ratpack {
  bindings {
    bind(UserService, DefaultUserService)
    bind(UserProfileService, DefaultUserProfileService)
  }
  handlers {
    prefix("user") {
      get(":username") { UserService userService, 
                         UserProfileService profileService ->
        userService.getUser(pathTokens.username).flatMap { user -> // <1>
          profileService.getUserProfile(pathTokens.username)
            .map { userProfile -> // <2>
              [
                username: user.username, 
                email: user.email,
                jobTitle: userProfile.jobTitle,
                phoneNumber: userProfile.phoneNumber
              ]
            }
        }.then { map -> // <3>
          render json(map)
        }
      }
    }
  }
}
