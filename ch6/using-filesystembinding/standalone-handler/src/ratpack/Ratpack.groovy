import app.FooBarFileHandler

import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    bind FooBarFileHandler
  }
  handlers {
    host("www.client1.com") { // <1>
      fileSystem("client1") { // <2>
        all(FooBarFileHandler)
      }
    }
    host("www.client2.com") { // <3>
      fileSystem("client2") { // <4>
        all(FooBarFileHandler)
      }
    }
  }
}
