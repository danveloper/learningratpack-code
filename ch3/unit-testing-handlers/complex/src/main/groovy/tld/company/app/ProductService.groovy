package tld.company.app

import groovy.transform.Immutable
import ratpack.exec.Promise

interface ProductService {

  Promise<Product> getProduct(Long id)

  @Immutable
  static class Product {
    Long id
    String name
    String description
    BigDecimal price
  }
}
