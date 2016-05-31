import ratpack.groovy.template.TextTemplateModule

import static ratpack.groovy.Groovy.groovyTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    module(TextTemplateModule)
  }
  handlers {
    get {
      render(groovyTemplate(
                [title: "Hello, Ratpack!", 
                welcomeMessage: "Welcome to Learning Ratpack!", 
                footerMessage: "Ratpack is Great!"], "welcome.html"))
    }
  }
}
