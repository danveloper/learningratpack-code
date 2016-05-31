import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    prefix("api") {
      get {
        render "got the API route"
      }
    }
    files {
      dir("static").indexFiles("index.html")
    }
  }
}
