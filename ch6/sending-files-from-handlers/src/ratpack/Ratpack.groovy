import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    get {
      render file("static/welcome.html")
    }
  }
}
