import app.MapUsernamePasswordAuthenticator
import app.SecurityConfig
import org.pac4j.http.client.indirect.IndirectBasicAuthClient
import org.pac4j.http.profile.creator.AuthenticatorProfileCreator
import ratpack.pac4j.RatpackPac4j
import ratpack.session.SessionModule

import static ratpack.groovy.Groovy.ratpack

ratpack {
  serverConfig { // <1>
    yaml("config.yml")
    sysProps()
    env()
    require("/security", SecurityConfig)
  }
  bindings {
    module SessionModule
  }
  handlers {
    all(
        RatpackPac4j.authenticator(
            new IndirectBasicAuthClient(
                new MapUsernamePasswordAuthenticator(
                    registry.get(SecurityConfig).basic.userPassMap), // <2>
                AuthenticatorProfileCreator.INSTANCE
            )
        )
    )
    get("auth") {
      RatpackPac4j.login(context, IndirectBasicAuthClient).then {
        redirect "/"
      }
    }
    get {
      RatpackPac4j.userProfile(context)
          .route { o -> o.present } { o -> render "Hello, ${o.get().id}!" }
          .then  { render "Not Authenticated!" }
    }
    get("logout") {
      RatpackPac4j.logout(context).then {
        redirect "/"
      }
    }
  }
}