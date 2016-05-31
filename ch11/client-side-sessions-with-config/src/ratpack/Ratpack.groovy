import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json
import ratpack.session.SessionModule
import ratpack.session.Session
import app.ViewTracker
import ratpack.session.clientside.ClientSideSessionModule
import ratpack.session.clientside.ClientSideSessionConfig

ratpack {
  serverConfig {
    yaml("config.yml")
    env()
    sysProps()
  }
  bindings {
    module(SessionModule)
    moduleConfig(ClientSideSessionModule,
        serverConfig.get("/session", ClientSideSessionConfig)) // <1>
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
