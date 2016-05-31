import tld.company.app.ProductHandler
import tld.company.app.ProductService
import tld.company.app.DefaultProductService

import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    bind(ProductService, DefaultProductService)
  }
  handlers {
    get("product/:id", new ProductHandler())
  }
}
