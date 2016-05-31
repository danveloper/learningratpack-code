@Grab('io.ratpack:ratpack-groovy:1.3.3')

import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    prefix("products") {
      get("list") {
        render "Product List"
      }
      get("get") {
        render "Product Get"
      }
      get("search") {
        render "Product Search"
      }
    }
  }
}
