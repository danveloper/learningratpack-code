import app.AppSpringConfig
import app.Product
import app.ProductRepository
import ratpack.exec.Blocking

import static ratpack.groovy.Groovy.ratpack
import static ratpack.spring.Spring.spring
import static ratpack.jackson.Jackson.json

def springRegistry = spring(AppSpringConfig)
def repo = springRegistry.get(ProductRepository)
def product1 = new Product(
  name: "Learning Ratpack", 
  description: "The Best Book on Ratpack so far", 
  price: 42.99
)
def product2 = new Product(
  name: "Programming Grails", 
  description: "Top 10 Book on Grails", 
  price: 38.99
)
repo.save([product1, product2])

ratpack {
  handlers {
    register(springRegistry)

    prefix("product") {
      get(":id") { ProductRepository productRepository ->
        def id = pathTokens.asLong("id")
        Blocking.get {
          productRepository.findOne(id)
        }.then { product ->
          if (product) {
            render(json(product))
          } else {
            response.status(404)
            render(json([status: "not_found"]))
          }
        }
      }
      get { ProductRepository productRepository ->
        Blocking.get {
          productRepository.findAll()
        }.then { products ->
          render(json(products))
        }
      }
    }
  }
}
