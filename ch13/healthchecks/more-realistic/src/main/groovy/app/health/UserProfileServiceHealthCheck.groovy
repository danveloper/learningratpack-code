package app.health

import app.model.ConnectionError
import app.model.RemoteServiceError
import app.services.UserProfileService
import ratpack.exec.Promise
import ratpack.health.HealthCheck
import ratpack.registry.Registry

class UserProfileServiceHealthCheck implements HealthCheck {
  final String name = "UserProfileServiceCheck" // <1>

  @Override
  Promise<HealthCheck.Result> check(Registry registry)
      throws Exception {
    def userProfileService = registry.get(UserProfileService)

    userProfileService.ping().promise()
      .map {
        HealthCheck.Result.healthy() // <2>
      }
      .mapError { error ->
        def msg = "The was an error with the remote service" // <3>
        if (error instanceof ConnectionError) {
          msg = "unable to connect to remote service" // <4>
        } else if (error instanceof RemoteServiceError) {
          msg = "protocol failure when pinging remote service" // <5>
        }
        HealthCheck.Result.unhealthy(msg, error) // <6>
      }
  }
}
