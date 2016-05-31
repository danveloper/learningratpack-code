import groovy.json.JsonSlurper

import static groovy.json.JsonOutput.toJson
import static ratpack.groovy.Groovy.ratpack

class User {
  String username
  String email
}

List<User> userStorage = []
JsonSlurper jsonSlurper = new JsonSlurper()

ratpack {

  handlers {
    path("api") {
      byMethod {
        post {
          request.body.map { body ->
            jsonSlurper.parseText(body.text) as Map
          }.map { data ->
            new User(data)
          }.then { user ->
            userStorage << user
            response.send()
          }
        }
        get {
          response.send(toJson(userStorage))
        }
      }
    }
  }
}
