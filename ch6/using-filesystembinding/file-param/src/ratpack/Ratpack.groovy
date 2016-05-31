import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    all {
      def fileParam = request.queryParams.file // <1>
      
      if (fileParam == "foo" || fileParam == "bar") { // <2>
        render(file("/html/${fileParam}.html"))
      } else { // <3>
        response.status(404)
        render(file("/html/error.html"))
      }
    }
  }
}
