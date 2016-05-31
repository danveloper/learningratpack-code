package tld.company.app

import spock.lang.Specification

class MyServiceSpec extends Specification {

  void "service calls should return proper response"() {
    setup:
    "Set up the service for testing"
    def service = new MyService()

    when:
    "Perform the service call"
    def result = service.doServiceCall()

    then: 
    "Ensure that the service call returned the proper result"
    result == "service was called"

    cleanup: 
    "Shutdown the service when this feature is complete"
    service.shutdown()
  }
}
