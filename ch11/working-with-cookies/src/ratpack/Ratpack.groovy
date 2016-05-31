import ratpack.groovy.template.TextTemplateModule
import ratpack.form.Form

import static ratpack.groovy.Groovy.groovyTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    module TextTemplateModule // <1>
  }
  handlers {
    post("updatePosition") {
      parse(Form).then { data -> // <2>
        response.cookie("ratpack-view-position", data.next_pos) // <3>
        redirect "/"
      }
    }
    get {
      def position = request.oneCookie("ratpack-view-position")?.toInteger() ?: 0 // <4>
      render groovyTemplate([position: position], "index.html") // 
    }
  }
}
