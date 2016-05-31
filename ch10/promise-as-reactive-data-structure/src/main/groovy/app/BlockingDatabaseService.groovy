package app

import javax.inject.Inject
import groovy.sql.Sql
import ratpack.exec.Promise
import ratpack.exec.Blocking

class BlockingDatabaseService {
  
  private final Sql sql

  @Inject
  BlockingDatabaseService(Sql sql) {
    this.sql = sql
  }

  Promise<User> getUser(String username) { // <1>
    Blocking.get {
      def row = sql.firstRow("SELECT * FROM USER WHERE USERNAME = $username")
      if (row) {
        new User(
          username: row["username"], 
          email: row["email"], 
          password: row["password"]
        )
      } else {
        null
      }
    }
  }

  Promise<List<User>> getUsers() { // <2>
    Blocking.get {
      sql.rows("SELECT * FROM USER").collect { row ->
        new User(
          username: row["username"], 
          email: row["email"], 
          password: row["password"]
        )
      }
    }
  }
}
