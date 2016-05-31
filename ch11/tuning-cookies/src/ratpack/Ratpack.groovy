import ratpack.groovy.template.TextTemplateModule
import ratpack.form.Form

import static ratpack.groovy.Groovy.groovyTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    module TextTemplateModule
  }
  handlers {
    post("updatePosition") {
      parse(Form).then { data ->
        def cookie = response.cookie("ratpack-view-position", data.next_pos)
        cookie.maxAge = 365 * 24 * 60 * 60 // <1>
        cookie.domain = "localhost" // <2>
        cookie.httpOnly = true // <3>
        cookie.secure = false // <4>
        redirect "/"
      }
    }
    get {
      def position = request.oneCookie("ratpack-view-position")?.toInteger() ?: 0
      render groovyTemplate([position: position], "index.html")
    }
  }
}
