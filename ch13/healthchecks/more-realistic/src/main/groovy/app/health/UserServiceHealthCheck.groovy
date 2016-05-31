package app.health

import app.services.UserService
import ratpack.exec.Promise
import ratpack.health.HealthCheck
import ratpack.registry.Registry

class UserServiceHealthCheck implements HealthCheck {
  final String name = "UserServiceCheck" // <1>

  @Override
  Promise<HealthCheck.Result> check(Registry registry) throws Exception {
    def userService = registry.get(UserService)

    userService.ping().promise() // <2>
      .map {
        HealthCheck.Result.healthy() // <3>
      }
      .mapError { error ->
        HealthCheck.Result.unhealthy(error) // <4>
      }
  }
}
