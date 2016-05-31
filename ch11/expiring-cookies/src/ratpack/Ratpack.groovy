import ratpack.groovy.template.TextTemplateModule
import ratpack.form.Form

import static ratpack.groovy.Groovy.groovyTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    module TextTemplateModule
  }
  handlers {
    post("resetView") { // <1>
      response.expireCookie("ratpack-view-position") // <2>
      redirect "/" // <3>
    }
    post("updatePosition") {
      parse(Form).then { data ->
        def cookie = response.cookie("ratpack-view-position", data.next_pos)
        cookie.maxAge = 365 * 24 * 60 * 60
        cookie.domain = "localhost"
        cookie.httpOnly = true
        cookie.secure = false
        redirect "/"
      }
    }
    get {
      def position = request.oneCookie("ratpack-view-position")?.toInteger() ?: 0
      render groovyTemplate([position: position], "index.html")
    }
  }
}
