import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    files { // <1>
      dir("static").indexFiles("index.html")
    }
  }
}
