package tld.company.app

import ratpack.handling.Handler
import ratpack.handling.Context

import static ratpack.jackson.Jackson.json

class ProductHandler implements Handler {

  @Override
  void handle(Context ctx) {
    ProductService productService = ctx.get(ProductService) // <1>

    def id = ctx.pathTokens.asLong("id") // <2>
    if (id != null) {
      productService.getProduct(id).then { product -> // <3>
        if (product) {
          ctx.render(json(product)) // <4>
        } else {
          ctx.response.status(404).send() // <5>
        }
      }
    } else {
      ctx.response.status(400) // <6>
      ctx.render(json([status: "error", message: "product id is required"])) // <7>
    }
  }
}
