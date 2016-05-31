import static ratpack.groovy.Groovy.ratpack
import ratpack.session.SessionModule
import ratpack.session.Session

ratpack {
  bindings {
    module(SessionModule) // <1>
  }
  handlers {
    all { Session session -> // <2>
      session.get("req-count").map { o -> // <3>
        o.orElse(0)
      }.flatMap { count -> // <4>
        session.set("req-count", count+1).promise() // <5>
      }.then {
        next() // <6>
      }
    }

    get { Session session ->
      session.get("req-count").then { o -> // <7>
        render o.get().toString() // <8>
      }
    }
  }
}
