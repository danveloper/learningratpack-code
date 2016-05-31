import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    prefix("static") {
      files {
        dir("static").indexFiles("index.html")
      }
    }
    prefix("api") {
      get {
        render "got the api route"
      }
    }
  }
}
