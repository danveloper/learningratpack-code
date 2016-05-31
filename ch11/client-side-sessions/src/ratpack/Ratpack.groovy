import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json
import ratpack.session.SessionModule
import ratpack.session.Session
import app.ViewTracker
import ratpack.session.clientside.ClientSideSessionModule
import java.time.Duration

ratpack {
  bindings {
    module(SessionModule)
    module(ClientSideSessionModule) { c ->
      c.sessionCookieName = "ratpack_session" // <1>
      c.secretToken = Math.floor(System.currentTimeMillis() / 10000) // <2>
      c.secretKey = '!c$mB&aGkL112345' // <3>
      c.macAlgorithm = "HmacSHA1" // <4>
      c.cipherAlgorithm = "AES/CBC/PKCS5Padding" // <5>
      c.maxSessionCookieSize = 1932 // <6>
      c.maxInactivityInterval = Duration.ofHours(24) // <7>
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
