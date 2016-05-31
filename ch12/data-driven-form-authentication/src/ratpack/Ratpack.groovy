import app.DatabaseUsernamePasswordAuthenticator
import groovy.sql.Sql
import org.pac4j.http.client.indirect.FormClient
import org.pac4j.http.profile.UsernameProfileCreator
import org.pac4j.http.profile.creator.AuthenticatorProfileCreator
import ratpack.groovy.sql.SqlModule
import ratpack.groovy.template.TextTemplateModule
import ratpack.hikari.HikariModule
import ratpack.pac4j.RatpackPac4j
import ratpack.service.Service
import ratpack.service.StartEvent
import ratpack.session.SessionModule

import static java.util.Collections.singletonMap
import static ratpack.groovy.Groovy.groovyTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    module SessionModule
    module TextTemplateModule
    module SqlModule
    module(HikariModule) { c ->
      c.dataSourceClassName = 'org.h2.jdbcx.JdbcDataSource'
      c.addDataSourceProperty 'URL', 'jdbc:h2:mem:test;DB_CLOSE_DELAY=-1'
      c.username = 'sa'
      c.password = ''
    }

    bindInstance new Service() {
      @Override
      void onStart(StartEvent e) throws Exception {
        Sql sql = e.registry.get(Sql)
        sql.execute "CREATE TABLE USER_AUTH(USER VARCHAR(255), PASS VARCHAR(255))"
        sql.execute "INSERT INTO USER_AUTH (USER, PASS) " + 
          "VALUES('learningratpack',  " +
            "'768122eeeebdafa3eb878f868b0e4e6a4944367aa635538f')"
      }
    }

    bind(DatabaseUsernamePasswordAuthenticator) // <1>
  }
  handlers {
    def callbackUrl = "auth" // <2>
    def formClient = new FormClient(
        callbackUrl,
        registry.get(DatabaseUsernamePasswordAuthenticator), // <3>
        AuthenticatorProfileCreator.INSTANCE
    )
    all(RatpackPac4j.authenticator(callbackUrl, formClient))

    get("login") {
      render(groovyTemplate(singletonMap("callbackUrl", callbackUrl), "login.html"))
    }

    get("logout") {
      RatpackPac4j.logout(context).then {
        redirect '/'
      }
    }

    get {
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
