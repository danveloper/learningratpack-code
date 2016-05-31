package tld.company.app

import ratpack.exec.Promise

class DefaultProductService implements ProductService {

  Promise<Product> getProduct(Long id) {
    def product = new Product(id, "foo", "A foo product, indeed.", 12.99)
    Promise.sync { product }
  }
}
