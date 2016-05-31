import static ratpack.groovy.Groovy.ratpack
import static groovy.json.JsonOutput.toJson

class DatabaseConfig {
  String host = "localhost"
  String user = "root"
  String password
  String db = "myDB"
}

class LandingPageConfig {
  String welcomeMessage = "Welcome to Ratpack!"
  String analyticsKey = "ua-12345678"
}

class ApplicationConfig { // <1>
  DatabaseConfig database = new DatabaseConfig()
  LandingPageConfig landing = new LandingPageConfig()
}

ratpack {
  serverConfig {
    json "dbconfig.json"
    env()
    sysProps()
    require("", ApplicationConfig) // <2>
  }
  handlers {
    get("config") { ApplicationConfig config ->
      render toJson(config)
    }
  }
}
