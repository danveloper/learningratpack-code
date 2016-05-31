import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    // ... application handlers go first ...

    when {
      request.headers.'user-agent' ==~ /.*MSIE.*/ // <1>
    } {
      files {
        dir("msie").indexFiles("index.htm") // <2>
      }
    }

    when {
      request.headers.'user-agent' ==~ /.*Chrome\\/[.0-9]* Mobile.*/ // <3>
    } {
      files {
        dir("mobile/chrome").indexFiles("index.html")
      }
    }

    files { // <4>
      dir("default").indexFiles("index.html")
    }
  }
}
