import app.*
import ratpack.groovy.template.TextTemplateModule
import static ratpack.groovy.Groovy.ratpack
import static ratpack.groovy.Groovy.groovyTemplate
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
  bindings {
    module TextTemplateModule
  }
  handlers {
    register(springRegistry)

    prefix("product") {
      get(":id") { ProductService productService ->
        def id = pathTokens.asLong("id")
        render productService.get(id) // <1>
      }
      get { ProductService productService ->
        productService.list().then { products ->
          byContent { // <2>
            json {
              render(json(products))
            }
            xml {
              // render XML of products
            }
            html {
              render groovyTemplate([products: products], "products.html")
            }
          }
        }
      }
    }
  }
}
