@Grab('io.ratpack:ratpack-groovy:1.3.3')

import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    get {
      render "Hello, World!"
    }
    path("foo") { 
      byMethod { 
        get {
          render "Hello, Foo Get!"
        }
        post {
          render "Hello, Foo Post!"
        }
      }
    }
  }
}
