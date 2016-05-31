import ratpack.hikari.HikariModule
import javax.sql.DataSource
import ratpack.exec.Blocking

import static ratpack.jackson.Jackson.json
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    module(HikariModule) { c -> // <1>
      c.dataSourceClassName = "com.mysql.jdbc.jdbc2.optional.MysqlDataSource" // <2>
      c.addDataSourceProperty "URL", "jdbc:mysql://localhost:3306/db" // <3>
      c.username = 'root'
    }
  }

  handlers {
    prefix("users") {
      get { DataSource dataSource -> // <4>
        Blocking.get {
          def conn = dataSource.connection
          def stmt = conn.createStatement()
          def ress = stmt.executeQuery("select * from user")
          def users = []
          while (ress.next()) {
            def username = ress.getString("username")
            def email = ress.getString("email")
            users << new User(username: username, email: email)
          }
          users
        } then { users ->
          render json(users)
        }
      }
    }
  }
}

class User {
  String username
  String email
}
