import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

import ratpack.groovy.sql.SqlModule
import ratpack.hikari.HikariModule
import app.*

import groovy.sql.Sql
import ratpack.service.Service
import ratpack.service.StartEvent

ratpack {
  bindings {
    module(SqlModule)
    module(HikariModule) { c ->
      c.dataSourceClassName = 'org.h2.jdbcx.JdbcDataSource'
      c.addDataSourceProperty 'URL', 'jdbc:h2:mem:test;DB_CLOSE_DELAY=-1'
      c.username = 'sa'
      c.password = ''
    }

    bind(UserService, DatabaseUserService)

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
    get("user") { UserService userService ->
      userService.getUsers()
        .filter { user -> // <1>
          !user.email.startsWith("noreply")
        }
        .map { user -> // <2>
          [
             username: user.username,
             email: user.email
          ]
        }
        .bindExec() // <3>
        .toList() // <4>
        .then { maps -> // <5>
          render json(maps)
        }
    }
  }
}
