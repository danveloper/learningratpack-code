import org.pac4j.http.client.indirect.IndirectBasicAuthClient
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator
import org.pac4j.http.profile.creator.AuthenticatorProfileCreator
import ratpack.pac4j.RatpackPac4j
import ratpack.session.SessionModule

import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    module SessionModule // <1>
  }
  handlers {
    all( // <2>
      RatpackPac4j.authenticator( // <3>
        new IndirectBasicAuthClient( // <4>
          new SimpleTestUsernamePasswordAuthenticator(), // <5>
          AuthenticatorProfileCreator.INSTANCE // <6>
        )
      )
    )
    get("auth") { // <7>
      RatpackPac4j.login(context, IndirectBasicAuthClient).then { // <8>
        redirect "/" // <9>
      }
    }
    get { // <10>
      RatpackPac4j.userProfile(context) // <11>
        .route { o -> o.present } { o -> render "Hello, ${o.get().id}!" } // <12>
        .then  { render "Not Authenticated!" } // <13>
    }
    get("logout") { // <14>
      RatpackPac4j.logout(context).then { // <15>
        redirect "/" // <16>
      }
    }
  }   
}
