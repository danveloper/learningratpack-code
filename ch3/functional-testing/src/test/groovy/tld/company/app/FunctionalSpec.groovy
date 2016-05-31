package tld.company.app

import ratpack.test.MainClassApplicationUnderTest
import spock.lang.Specification

class FunctionalSpec extends Specification {

  void "default handler should render Hello, World!"() {
    setup:
    def aut = new MainClassApplicationUnderTest(Main)

    when:
    def response = aut.httpClient.text

    then:
    response == "Hello, World!"

    cleanup:
    aut.close()
  }
}
