import static ratpack.groovy.Groovy.ratpack
import static groovy.json.JsonOutput.toJson

class User {
  String username
  String email
}

ratpack {
  handlers {
    get {
      response.contentType("application/json")
      response.send(toJson(new User(username: "dan", email: "danielpwoods@gmail.com")))
    }
  }
}
