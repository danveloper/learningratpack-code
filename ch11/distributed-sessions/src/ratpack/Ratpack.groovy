import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json
import ratpack.session.SessionModule
import ratpack.session.Session
import ratpack.session.store.RedisSessionModule
import app.ViewTracker

ratpack {
  bindings {
    module(SessionModule)
    module(RedisSessionModule) { c ->
      c.host = "127.0.0.1" // <2>
      c.port = 6379 // <3>
    }
  }
  handlers {
    all { Session session ->
      session.get("view-tracker").flatMap { o ->
        def tracker = o.orElse(new ViewTracker())
        tracker.increment(request.uri) 
        session.set("view-tracker", tracker).promise() 
      }.then { 
        next()
      }
    }

    all { Session session ->
      session.get("view-tracker").then { o ->
        def tracker = o.get()
        render json(tracker.list()) 
      }
    }
  }
}
