import ratpack.session.Session
import ratpack.session.SessionModule

import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    module(SessionModule) // <1>
  }

  handlers {
    all { Session session ->
      session.set("my-value", "ratpack rules!").then {
        next()
      }
    }

    get { Session session ->
      render session.require("my-value")
    }
  }
}
