import static ratpack.groovy.Groovy.ratpack
import static groovy.json.JsonOutput.toJson

import app.* // <1>

ratpack {
  serverConfig {
    json "dbconfig.json"
    yaml "landingpage.yml"
    add new CustomConfigSource() // <2>
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
