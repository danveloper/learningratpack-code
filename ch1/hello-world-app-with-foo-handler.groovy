@Grab('io.ratpack:ratpack-groovy:1.3.3')

import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    get {
      render "Hello, World!"
    }
    get("foo") {
      render "Hello, Foo!"
    }
  }
}
