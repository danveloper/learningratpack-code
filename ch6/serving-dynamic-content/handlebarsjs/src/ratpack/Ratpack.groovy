import ratpack.handlebars.HandlebarsModule

import static ratpack.groovy.Groovy.ratpack
import static ratpack.handlebars.Template.handlebarsTemplate

ratpack {
  bindings {
    module HandlebarsModule
  }
  handlers {
    get {
      render(handlebarsTemplate("welcome", [
        title: "Hello, Ratpack!", 
        welcomeMessage: "Welcome to Learning Ratpack!", 
        footerMessage: "Ratpack is Great!"], "text/html"))
    }
  }
}
