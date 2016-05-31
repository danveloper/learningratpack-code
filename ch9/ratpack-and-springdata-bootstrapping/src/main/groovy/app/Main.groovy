package app

import ratpack.server.RatpackServer
import ratpack.server.BaseDir
import ratpack.exec.Blocking

import static ratpack.spring.Spring.spring
import static ratpack.jackson.Jackson.json

class Main {
  static void main(args) {
    RatpackServer.start { spec -> spec
      .serverConfig { b -> b // <1>
        .yaml("bootstrap.yml") // <2>
        .sysProps()
        .env()
        .require("/product", ProductBootstrapConfig) // <3>
        .baseDir(BaseDir.find())
        .build()
      }
      .registry(spring(AppSpringConfig)) // <1>
      .handlers { chain ->
         chain.prefix("product") { pchain ->
           pchain.get(":id") { ctx ->
             ProductRepository productRepository = ctx.get(ProductRepository) // <2>
             def id = ctx.pathTokens.asLong("id")
             Blocking.get {
               productRepository.findOne(id)
             }.then { product ->
               if (product) {
                 ctx.render(json(product))
               } else {
                 ctx.response.status(404)
                 ctx.render(json([status: "not_found"]))
               }
             }
           }
           pchain.get { ctx ->
             ProductRepository productRepository = ctx.get(ProductRepository) // <3>
             Blocking.get {
               productRepository.findAll()
             }.then { products ->
               ctx.render(json(products))
             }
           }
         }
      }
    }
  }
}
