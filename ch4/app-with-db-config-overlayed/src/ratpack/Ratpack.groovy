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
    json Class.getResource("/config/dbconfig.json") // <2>
    json Paths.get("/etc/dbconfig.json") // <3>
    require("/database", DatabaseConfig)
  }
  handlers {
    get("config") { DatabaseConfig config ->
      render toJson(config)
    }
  }
}
