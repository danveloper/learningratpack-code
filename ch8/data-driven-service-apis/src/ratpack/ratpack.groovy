import app.GroovySqlPersonService
import app.Person
import app.PersonService
import groovy.sql.Sql
import org.h2.jdbcx.JdbcDataSource
import ratpack.form.Form
import ratpack.groovy.sql.SqlModule
import ratpack.service.Service
import ratpack.service.StartEvent

import javax.sql.DataSource

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

ratpack {
  bindings {
    module SqlModule
    bindInstance DataSource, new JdbcDataSource(
      URL: "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", 
      user: "sa", 
      password: ""
    )

    bind PersonService, GroovySqlPersonService // <1>

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
    get { PersonService personService -> // <2>
       personService.list().then { personList -> // <3>
        render(json(personList))
      }
    }
    post("create") { PersonService personService -> // <4>
      parse(Form).then { f ->
        def name = f.name
        if (name) {
          def person = new Person(name: name) // <5>
          personService.save(person).onError { t -> // <6>
            render(json([success: false, error: t.message]))
          } then {
            render(json([success: true]))
          }
        } else {
          response.status(400)
          render(json([success: false, error: "name is required"]))
        }
      }
    }
  }
}
