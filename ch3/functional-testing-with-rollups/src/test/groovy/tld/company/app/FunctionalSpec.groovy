package tld.company.app

import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import spock.lang.AutoCleanup
import spock.lang.Specification
import spock.lang.Unroll

class FunctionalSpec extends Specification {

  @AutoCleanup
  def aut = new GroovyRatpackMainApplicationUnderTest()

  @Unroll
  void "should render #expected for #userAgent clients"() {
    when:
    def response = aut.httpClient.requestSpec { spec ->
      spec.headers.'User-Agent' = [userAgent]
    }.get("api").body.text

    then:
    response == expected

    where:
    userAgent                  | expected
    "Microservice Client v1.0" | "V1 Model"
    "Microservice Client v2.0" | "V2 Model"
    "Microservice Client v3.0" | "V3 Model"
  }
}
