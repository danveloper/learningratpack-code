package app

import javax.inject.Inject
import groovy.sql.Sql
import org.reactivestreams.Publisher
import ratpack.exec.Blocking

class DatabaseUserService implements UserService {
  
  private final Sql sql

  @Inject
  DatabaseUserService(Sql sql) {
    this.sql = sql
  }

  Publisher<User> getUsers() {
    Blocking.get { // <1>
      sql.rows "SELECT * FROM USER"
    }
    .publish() // <2>
  }
}
