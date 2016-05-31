import static ratpack.groovy.Groovy.ratpack
import static groovy.json.JsonOutput.toJson

import java.nio.file.Paths

class DatabaseConfig {
  String host = "localhost"
  String user = "root"
  String password
  String db = "myDB"
}

ratpack {
  serverConfig {
    json "dbconfig.json" // <1>
    yaml Paths.get("/etc/dbconfig.yml") // <2>
    require("/database", DatabaseConfig)
  }
  handlers {
    get("config") { DatabaseConfig config ->
      render toJson(config)
    }
  }
}
