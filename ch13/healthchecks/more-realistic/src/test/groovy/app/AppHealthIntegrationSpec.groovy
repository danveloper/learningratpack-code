package app

import app.health.UserProfileServiceHealthCheck
import app.health.UserServiceHealthCheck
import app.model.ConnectionError
import app.model.RemoteServiceError
import app.services.UserProfileService
import app.services.UserService
import ratpack.exec.Operation
import ratpack.groovy.test.embed.GroovyEmbeddedApp
import ratpack.health.HealthCheckHandler
import spock.lang.AutoCleanup
import spock.lang.Specification

class AppHealthIntegrationSpec extends Specification {

  def userProfileService = Mock(UserProfileService) // <1>
  def userService = Mock(UserService) // <2>
  def userServiceHealthCheck = new UserServiceHealthCheck() // <3>
  def userProfileServiceHealthCheck = new UserProfileServiceHealthCheck() // <4>

  @AutoCleanup
  @Delegate
  GroovyEmbeddedApp app = GroovyEmbeddedApp.of { // <5>
    registryOf { // <6>
      add(userProfileService)
      add(userService)
      add(userServiceHealthCheck)
      add(userProfileServiceHealthCheck)
    }
    handlers {
      get("health", new HealthCheckHandler()) // <7>
    }
  }

  void "should return healthy checks"() {
    when:
    def response = httpClient.get("health")

    then:
    1 * userProfileService.ping() >> Operation.noop()
    1 * userService.ping() >> Operation.noop()
    response.statusCode == 200
  }

  void "should fail when userService is offline"() {
    when:
    def response = httpClient.get("health")

    then:
    1 * userProfileService.ping() >> Operation.noop()
    1 * userService.ping() >> Operation.of { throw new RuntimeException("fail") }
    response.statusCode == 503
    response.body.text.contains("${userServiceHealthCheck.name} : UNHEALTHY")
    response.body.text.contains("${userProfileServiceHealthCheck.name} : HEALTHY")
  }

  void "should fail when userProfileService is offline"() {
    when:
    def response = httpClient.get("health")

    then:
    1 * userProfileService.ping() >> Operation.of {
      throw new RuntimeException("fail")
    }
    1 * userService.ping() >> Operation.noop()
    response.statusCode == 503
    response.body.text.contains("${userServiceHealthCheck.name} : HEALTHY")
    response.body.text.contains("${userProfileServiceHealthCheck.name} : UNHEALTHY")
  }

  void "should indicate proper failure type for userProfileService"() {
    when:
    def response = httpClient.get("health")

    then:
    1 * userService.ping() >> Operation.noop()
    1 * userProfileService.ping() >> Operation.of {
      throw error
    }
    response.body.text.
      contains("${userProfileServiceHealthCheck.name} : UNHEALTHY " +
        "[${message}]")

    where:
    error                    | message
    new ConnectionError()    | "unable to connect to remote service"
    new RemoteServiceError() | "protocol failure when pinging remote service"
    new RuntimeException()   | "The was an error with the remote service"
  }
}
