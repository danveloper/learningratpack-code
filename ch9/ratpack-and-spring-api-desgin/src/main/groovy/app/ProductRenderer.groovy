package app

import org.springframework.stereotype.Component
import ratpack.handling.Context
import ratpack.render.Renderer
import ratpack.groovy.Groovy

import static ratpack.jackson.Jackson.json

@Component // <1>
class ProductRenderer implements Renderer<Product> {

  @Override
  Class<Product> getType() {
    Product
  }

  @Override
  void render(Context ctx, Product product) throws Exception {
    ctx.byContent { spec -> spec// <2>
      .json {
        ctx.render(json(product))
      }
      .xml {
        // provide XML rendering
      }
      .html {
        ctx.render(Groovy.groovyTemplate([product: product], "product.html"))
      }
    }
  }
}
