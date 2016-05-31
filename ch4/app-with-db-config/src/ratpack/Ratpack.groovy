import static ratpack.groovy.Groovy.ratpack
import static groovy.json.JsonOutput.toJson

class DatabaseConfig { // <1>
  String host = "localhost"
  String user = "root"
  String password
  String db = "myDB"
}

ratpack {
  serverConfig { // <2>
    json "dbconfig.json" // <3>
    require("/database", DatabaseConfig) // <4>
  }
  handlers {
    get("config") { DatabaseConfig config -> // <5>
      render toJson(config)
    }
  }
}
