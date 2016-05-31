package tld.company.app

import ratpack.test.handling.RequestFixture
import spock.lang.Specification

class AppHandlerSpec extends Specification {

  def handler = new AppHandler() // <1>

  void "should render proper response"() {
    given:
    def result = RequestFixture.handle(handler) {} // <2>

    expect:
    result.bodyText == "ok" // <3>
  }
}
