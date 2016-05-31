import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json
import ratpack.session.SessionModule
import ratpack.session.Session
import app.ViewTracker

ratpack {
  bindings {
    module(SessionModule)
  }
  handlers {
    all { Session session ->
      session.get("view-tracker").flatMap { o -> // <1>
        def tracker = o.orElse(new ViewTracker()) // <2>
        tracker.increment(request.uri) // <3>
        session.set("view-tracker", tracker).promise() // <4>
      }.then { 
        next()
      }
    }

    all { Session session ->
      session.get("view-tracker").then { o ->
        def tracker = o.get()
        render json(tracker.list()) // <5>
      }
    }
  }
}
