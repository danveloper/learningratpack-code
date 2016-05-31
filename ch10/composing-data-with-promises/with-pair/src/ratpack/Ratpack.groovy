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
      get(":username") { UserService userService, UserProfileService userProfileService ->
        def username = pathTokens.username
        userService.getUser(username)
                   .right(userProfileService.getUserProfile(username))
                   .map { pair ->
                      def user = pair.left
                      def userProfile = pair.right
                      [
                        username: user.username, 
                        email: user.email,
                        jobTitle: userProfile.jobTitle,
                        phoneNumber: userProfile.phoneNumber
                      ]
                   }
                   .then { map ->
                      render json(map)
                   }
      }
    }
  }
}
