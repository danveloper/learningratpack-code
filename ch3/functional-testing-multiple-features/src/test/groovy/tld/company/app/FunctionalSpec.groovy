package tld.company.app

import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import spock.lang.AutoCleanup
import spock.lang.Specification

class FunctionalSpec extends Specification {

  @AutoCleanup
  def aut = new GroovyRatpackMainApplicationUnderTest()

  void "should properly render for v1.0 clients"() {
    when:
    def response = aut.httpClient.requestSpec { spec ->
      spec.headers.'User-Agent' = ["Microservice Client v1.0"]
    }.get("api").body.text

    then:
    response == "V1 Model"
  }

  void "should properly render for v2.0 clients"() {
    when:
    def response = aut.httpClient.requestSpec { spec ->
      spec.headers.'User-Agent' = ["Microservice Client v2.0"]
    }.get("api").body.text

    then:
    response == "V2 Model"
  }

  void "should properly render for v3.0 clients"() {
    when:
    def response = aut.httpClient.requestSpec { spec ->
      spec.headers.'User-Agent' = ["Microservice Client v3.0"]
    }.get("api").body.text

    then:
    response == "V3 Model"
  }
}
