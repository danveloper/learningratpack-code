@Grab('io.ratpack:ratpack-groovy:1.3.3')

import static ratpack.groovy.Groovy.ratpack
import static groovy.json.JsonOutput.toJson

class User {
  String username
  String email
}

def user1 = new User(
  username: "ratpack",
  email: "ratpack@ratpack.io"
)
def user2 = new User(
  username: "danveloper",
  email: "danielpwoods@gmail.com"
)

def users = [user1, user2]

ratpack {
  handlers {
    get("users") {
      byContent {
        html {
          // ... snipped for brevity ...
        }
        json {
          // ... snipped for brevity ...
        }
        xml {
          // ... snipped for brevity ...
        }
        type("application/vnd.app.custom+json") {
          // ... snipped for brevity ...
        }
        noMatch { // <1>
          response.status 400
          render "negotiation not possible."
        }
      }
    }
  }
}
