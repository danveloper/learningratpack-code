import static ratpack.groovy.Groovy.ratpack
import ratpack.ssl.SSLContexts
import java.nio.file.Paths

ratpack {
  serverConfig {
    ssl SSLContexts.sslContext(Paths.get("/etc/server.jks"), "changeit") // <1>
  }
  handlers {
    all {
      render "Hello, SSL World!"
    }
  }
}
