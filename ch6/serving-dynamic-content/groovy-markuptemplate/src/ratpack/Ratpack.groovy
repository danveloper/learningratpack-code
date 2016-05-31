import ratpack.groovy.template.MarkupTemplateModule

import static ratpack.groovy.Groovy.ratpack
import static ratpack.groovy.Groovy.groovyMarkupTemplate

ratpack {
  bindings {
    module(MarkupTemplateModule)
  }
  handlers {
    get {
      render(groovyMarkupTemplate([
               title: "Hello, Ratpack!", 
               welcomeMessage: "Welcome to Learning Ratpack!", 
               footerMessage: "Ratpack is Great!"], "welcome.gtpl"))
    }
  }
}
