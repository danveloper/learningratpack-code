package app

import com.google.inject.Inject
import groovy.sql.Sql
import ratpack.exec.Blocking
import ratpack.exec.Operation
import ratpack.exec.Promise

class GroovySqlPersonService implements PersonService {
  private final Sql sql

  @Inject
  GroovySqlPersonService(Sql sql) {
    this.sql = sql
  }

  @Override
  Promise<List<Person>> list() {
    Blocking.get {
      sql.rows("select * from test").collect {
        def id = (long)it["id"]
        def name = it["name"]
        new Person(id: id, name: name)
      }
    }
  }

  @Override
  Operation save(Person person) {
    Blocking.get {
      sql.execute "INSERT INTO TEST (NAME) VALUES($person.name)"
    }.operation()
  }
}
