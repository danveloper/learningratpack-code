import org.pac4j.http.client.indirect.FormClient
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator
import org.pac4j.http.profile.creator.AuthenticatorProfileCreator

import static ratpack.groovy.Groovy.ratpack
import static ratpack.groovy.Groovy.groovyTemplate
import static java.util.Collections.singletonMap

import ratpack.session.SessionModule
import ratpack.pac4j.RatpackPac4j
import ratpack.groovy.template.TextTemplateModule

ratpack {
  bindings {
    module SessionModule
    module TextTemplateModule // <1>
  }
  handlers {
    def formClient = new FormClient( // <2>
      "auth",
      new SimpleTestUsernamePasswordAuthenticator(),
      AuthenticatorProfileCreator.INSTANCE
    )

    all(RatpackPac4j.authenticator("auth", formClient)) // <3>

    get("login") { // <4>
      render(groovyTemplate(
              singletonMap("callbackUrl", formClient.loginUrl),
              "login.html"))    
    }

    get("logout") { // <5>
      RatpackPac4j.logout(context).then {
        redirect '/'
      }
    }

    get { // <6>
      RatpackPac4j.userProfile(context)
        .route { o -> o.present } { o -> 
          render(groovyTemplate([profile: o.get()], "protectedIndex.html"))
        }
        .then {
          render(groovyTemplate([:], "index.html"))
        }
    }
  }
}
