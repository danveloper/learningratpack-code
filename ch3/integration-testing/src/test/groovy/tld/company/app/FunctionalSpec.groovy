package tld.company.app

import groovy.json.JsonSlurper
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import spock.lang.AutoCleanup
import spock.lang.Specification

import static groovy.json.JsonOutput.toJson

class FunctionalSpec extends Specification {

  private static final JsonSlurper jsonSlurper = new JsonSlurper()

  @AutoCleanup
  def aut = new GroovyRatpackMainApplicationUnderTest()

  void "bootstrap data and properly render it back"() {
    setup:
    def user = [username: "danveloper", email: "danielpwoods@gmail.com"]

    when:
    def response = aut.httpClient.requestSpec { spec ->
      spec.body { b ->
        b.text(toJson(user))
      }
    }.post('api')

    then:
    response.statusCode == 200

    when:
    def json = aut.httpClient.get('api').body.text

    and:
    def users = jsonSlurper.parseText(json) as List

    then:
    users == [user]
  }
}
