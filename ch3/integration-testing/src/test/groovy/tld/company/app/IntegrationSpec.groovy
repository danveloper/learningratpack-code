package tld.company.app

import ratpack.exec.Promise
import ratpack.registry.Registry
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.exec.ExecHarness
import ratpack.impose.ImpositionsSpec
import ratpack.impose.UserRegistryImposition
import spock.lang.AutoCleanup
import spock.lang.Specification

import static groovy.json.JsonOutput.toJson

class IntegrationSpec extends Specification {
  UserService mockUserService = Mock(UserService) // <1>

  @AutoCleanup
  ExecHarness harness = ExecHarness.harness()

  @AutoCleanup
  def aut = new GroovyRatpackMainApplicationUnderTest() {
    @Override
    protected void addImpositions(ImpositionsSpec impositions) { // <2>
      impositions.add(UserRegistryImposition.of(
        Registry.of { r ->
          r.add(UserService, mockUserService)
        }
      ))
    }
  }

  void "should convert and save user data"() {
    setup:
    def userMap = [username: "danveloper", email: "danielpwoods@gmail.com"]

    when:
    aut.httpClient.requestSpec { spec ->
      spec.body { b ->
        b.text(toJson(userMap))
      }
    }.post('api')

    then:
    1 * mockUserService.save(_) >> { User user ->
      assert user.email == userMap.email
      assert user.username == userMap.username
      Promise.sync { null }
    }
  }

  void "should render user list as json"() {
    setup:
    def users = [
        new User(username: "danveloper", email: "danielpwoods@gmail.com"),
        new User(username: "kenkousen", email: "ken@kousenit.com"),
        new User(username: "ldaley", email: "ld@ldaley.com")
    ]

    when:
    def response = aut.httpClient.get("api")

    then:
    1 * mockUserService.getUsers() >> Promise.sync { users }
    response.body.text == toJson(users)
  }
}
