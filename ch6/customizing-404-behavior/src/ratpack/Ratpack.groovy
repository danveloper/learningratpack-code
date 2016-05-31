import app.CustomErrorHandler
import ratpack.error.ClientErrorHandler
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    // Binds the ClientErrorHandler interface to the 
    // project's CustomErrorHandler implementation.
    bindInstance(ClientErrorHandler, new CustomErrorHandler())
  }
  handlers {
    get {
      render(file("static/welcome.html"))
    }
  }
}
