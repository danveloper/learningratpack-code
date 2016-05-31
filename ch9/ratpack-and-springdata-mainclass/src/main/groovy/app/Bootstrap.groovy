package app

import org.springframework.stereotype.Component
import ratpack.service.Service
import ratpack.service.StartEvent

/**
 * I'm cheating a little bit with this class, because we don't discuss its function in the chapter...
 * The purpose here is simply to stand up some test data so that you can see the application work.
 * It isn't relevant to the conversation discussed in the chapter text.
 */
@Component
class Bootstrap implements Service {

  void onStart(StartEvent e) throws Exception {
    def repo = e.registry.get(ProductRepository)
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
  }
}
