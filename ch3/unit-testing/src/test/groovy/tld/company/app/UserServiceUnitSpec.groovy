package tld.company.app

import ratpack.test.exec.ExecHarness
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class UserServiceUnitSpec extends Specification {
  private static final def users = [
      new User(username: "danveloper", email: "danielpwoods@gmail.com"),
      new User(username: "kenkousen", email: "ken@kousenit.com"),
      new User(username: "ldaley", email: "ld@ldaley.com")
  ]

  @AutoCleanup
  ExecHarness execHarness = ExecHarness.harness() // <1>

  @Subject @Shared
  UserService userService = new DefaultUserService()

  void "should save and return user list"() {
    given:
    execHarness.yield { // <2>
      users.each { user -> userService.save(user) }
    }

    expect:
    execHarness.yieldSingle { // <3>
      userService.getUsers() 
    }.value == users
  }
}
