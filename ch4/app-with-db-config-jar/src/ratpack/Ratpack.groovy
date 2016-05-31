import static ratpack.groovy.Groovy.ratpack
import static groovy.json.JsonOutput.toJson

class DatabaseConfig {
  String host = "localhost"
  String user = "root"
  String password
  String db = "myDB"
}

ratpack {
  serverConfig {
    json Class.getResource("/config/dbconfig.json") // <1>
    require("/database", DatabaseConfig)
  }
  handlers {
    get("config") { DatabaseConfig config ->
      render toJson(config)
    }
  }
}
