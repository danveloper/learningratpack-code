package tld.company.app

import ratpack.exec.Promise
import ratpack.jackson.JsonRender
import ratpack.test.handling.RequestFixture
import spock.lang.Specification

class ProductHandlerSpec extends Specification {

  def handler = new ProductHandler() // <1>
  def productService = Mock(ProductService) // <2>
  def product = new ProductService.Product([ // <3>
      id: 1,
      name: "Learning Ratpack",
      description: "Simple, lean, powerful web applications",
      price: 49.99
  ])
  def requestFixture = RequestFixture.requestFixture() // <4>
      .registry { r ->
        r.add(ProductService, productService)
      }

  void "should properly render valid product requests"() { // <5>
    when:
    "a valid product is requested"
    def result = requestFixture.pathBinding([id: product.id.toString()]) // <6>
        .handle(handler)

    then:
    "it should be properly rendered back"
    1 * productService.getProduct(product.id) >> Promise.sync { product } // <7>
    result.rendered(JsonRender).object == product // <8>
  }

  void "should return not found for an invalid product"() { // <9>
    when:
    "an invalid product is requested"
    def result = requestFixture
        .pathBinding([id: "0"]) // <10>
        .handle(handler)

    then:
    "a 404 status should be sent back"
    1 * productService.getProduct(0) >> Promise.sync { null } // <11>
    result.status.code == 404 // <12>
  }

  void "should return bad request for an invalid request"() { // <13>
    when:
    "no id is specified"
    def result = requestFixture.handle(handler) // <14>

    then:
    "a 400 status should be sent back"
    0 * productService.getProduct(_) // <15>
    result.status.code == 400 // <16>
  }
}
