import ratpack.session.SessionModule
import ratpack.session.Session
import ratpack.registry.Registry
import ratpack.form.Form

import static ratpack.groovy.Groovy.ratpack

class User {
  String username
  
  boolean isAdmin() {
    return username == "admin"
  }
}

ratpack {
  bindings {
    module SessionModule
  }
  handlers {
    post("login") { Session session ->
      parse(Form).flatMap { form ->
        session.getData().map { sessionData ->
          if (form.username == "admin" && form.password == "password") {
            sessionData.set("username", "admin")
          } else {
            sessionData.set("username", "anonymous")
          }
          sessionData
        }
      }.then { sessionData ->
        redirect "/"
      }
    }
    all { Session session -> // <1>
      session.getData().then { sessionData ->
        def usernameOption = sessionData.get("username")
        def user = new User(username: usernameOption.present ? // <2>
                                        usernameOption.get() : 
                                        "guest"
        )
        next(Registry.single(User, user)) // <3>
      }
    }
    when { // <4>
      def user = get(User)
      user.isAdmin()
    } {
      prefix("adminApi") { // <5>
        get {
          render "got the admin api"
        }
      }
      files { // <6>
        dir("admin").indexFiles("index.html")
      }
    }
    when { // <7>
      def user = get(User)
      !user.isAdmin()
    } {
      files { // <8>
        dir("user").indexFiles("index.html")
      }
    }
  }
}
