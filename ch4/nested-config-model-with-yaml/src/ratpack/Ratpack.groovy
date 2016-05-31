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

class ApplicationConfig {
  DatabaseConfig database
  LandingPageConfig landing
}

ratpack {
  serverConfig {
    json "dbconfig.json"
    yaml "landingpage.yml" // <1>
    env()
    sysProps()
    require("", ApplicationConfig)
  }
  handlers {
    get("config") { ApplicationConfig config ->
      render toJson(config)
    }
  }
}
