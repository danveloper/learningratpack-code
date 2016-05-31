import ratpack.groovy.sql.SqlModule
import ratpack.hikari.HikariModule
import app.BlockingDatabaseService
import ratpack.service.Service
import ratpack.service.StartEvent
import groovy.sql.Sql

import static ratpack.jackson.Jackson.json
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    module(SqlModule)
    module(HikariModule) { c ->
      c.dataSourceClassName = 'org.h2.jdbcx.JdbcDataSource'
      c.addDataSourceProperty 'URL', 'jdbc:h2:mem:test;DB_CLOSE_DELAY=-1'
      c.username = 'sa'
      c.password = ''
    }

    bind(BlockingDatabaseService)

    // for demo purposes
    bindInstance new Service() {
      @Override
      void onStart(StartEvent e) throws Exception {
        def sql = e.registry.get(Sql)
        sql.execute("CREATE TABLE USER(USERNAME VARCHAR(255), EMAIL VARCHAR(255), PASSWORD VARCHAR(255))")
        sql.execute "INSERT INTO USER (USERNAME,EMAIL,PASSWORD) VALUES ('learningratpack', 'lr@ratpack.io', 'd13fbf77594580dfcfb9d19a9b6d2327')"
        sql.execute "INSERT INTO USER (USERNAME,EMAIL,PASSWORD) VALUES ('dan', 'danielpwoods@gmail.com', 'd13fbf77594580dfcfb9d19a9b6d2327')"
      }
    }
  }
  handlers {
    prefix("user") {
      get(":username") { BlockingDatabaseService db ->
        db.getUser(pathTokens.username).map { user ->
          user ? [username: user.username, email: user.email] : null // <1>
        }.route { u -> u == null } { u -> // <2>
          response.status(404)
          render json([error: "not found"])
        }.then { user -> // <3>
          render json(user)
        }
      }
      get { BlockingDatabaseService db ->
        db.getUsers().map { users ->
          users.collect { u ->
            [username: u.username, email: u.email]
          }
        }.then { maps ->
          render json(maps)
        }
      }
    }
  }
}
