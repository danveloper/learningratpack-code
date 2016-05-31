import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    get {
      request.body.map { // <1>
        throw new RuntimeException() // <2>
      }.onError { t -> // <3>
        render "There was an error."
      }.then { // <4>
        render "There was not an error."
      }
    }
  }
}
