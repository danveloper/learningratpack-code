import ratpack.thymeleaf.ThymeleafModule

import static ratpack.groovy.Groovy.ratpack
import static ratpack.thymeleaf.Template.thymeleafTemplate

ratpack {
  bindings {
    module ThymeleafModule
  }
  handlers {
    get {
      render(thymeleafTemplate([
               title: "Hello, Ratpack!", 
               welcomeMessage: "Welcome to Learning Ratpack!", 
               footerMessage: "Ratpack is Great!"], "welcome"))
    }
  }
}
