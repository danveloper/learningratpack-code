import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    // ... Application handlers go first ...

    all { // <1>
      int cacheTime = 60 * 60 * 24 * 365 // one year
      response.headers.add("Cache-Control", "max-age=$cacheTime, public")
      next()
    }
    files { // <2>
      dir("static").indexFiles("index.html")
    } 
  }
}
