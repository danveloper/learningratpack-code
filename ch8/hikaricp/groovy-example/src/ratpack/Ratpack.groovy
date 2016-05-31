import groovy.sql.Sql
import ratpack.exec.Blocking
import ratpack.form.Form
import ratpack.groovy.sql.SqlModule
import ratpack.hikari.HikariModule
import ratpack.service.Service
import ratpack.service.StartEvent

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

ratpack {
  bindings {
    module SqlModule
    module(HikariModule) { c -> // <1>
      c.dataSourceClassName = 'org.h2.jdbcx.JdbcDataSource' // <2>
      c.addDataSourceProperty 'URL', 'jdbc:h2:mem:test;DB_CLOSE_DELAY=-1' // <3>
      c.username = 'sa' // <4>
      c.password = ''
    }

    bindInstance new Service() {
      void onStart(StartEvent e) throws Exception {
        Sql sql = e.registry.get(Sql)
        sql.execute(
          "CREATE TABLE TEST(ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255))"
        )
        sql.execute "INSERT INTO TEST (NAME) VALUES('Luke Daley')"
        sql.execute "INSERT INTO TEST (NAME) VALUES('Rob Fletch')"
        sql.execute "INSERT INTO TEST (NAME) VALUES('Dan Woods')"
      }
    }
  }
  handlers {
    get { Sql sql ->
      Blocking.get {
        sql.rows("select * from test").collect {
          it["name"]
        }
      } then { names ->
        render(json(names))
      }
    }
    post("create") { Sql sql ->
      parse(Form).then { f -> // <1>
        def name = f.name
        if (name) { // <2>
          Blocking.get { // <3>
            sql.execute "INSERT INTO TEST (NAME) VALUES($name)" // <4>
          } onError { t ->
            render(json([success: false, error: t.message])) // <5>
          } then {
            render(json([success: true])) // <6>
          }
        } else {
          response.status(400) //
          render(json([success: false, error: "name is required"]))
        }
      }
    }
  }
}
