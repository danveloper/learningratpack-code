package app;

import javax.sql.DataSource;
import ratpack.server.RatpackServer;
import ratpack.hikari.HikariModule;
import ratpack.guice.Guice;
import ratpack.exec.Blocking;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import static ratpack.jackson.Jackson.json;

public class Main {
  
  public static void main(String[] args) throws Exception {
    RatpackServer.start(spec -> spec
      .registry(Guice.registry(b -> b // <1>
        .module(HikariModule.class, config -> { // <2>
          config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource"); // <3>
          config.addDataSourceProperty("URL", "jdbc:mysql://localhost:3306/db");
          config.setUsername("root");
        })
      ))
      .handlers(chain -> chain
        .prefix("users", pchain -> pchain
          .get(ctx -> {
            DataSource dataSource = ctx.get(DataSource.class); // <4>
            Blocking.get(() -> {
              Connection conn = dataSource.getConnection();
              Statement stmt = conn.createStatement();
              ResultSet ress = stmt.executeQuery("select * from user");
              List<User> users = new ArrayList<>();
              while (ress.next()) {
                String username = ress.getString("username");
                String email = ress.getString("email");
                User user = new User();
                user.username = username;
                user.email = email;
                users.add(user);
              }
              return users;
            }).then(users -> ctx.render(json(users)));
          })
        )
      )
    );
  }

  private static class User {
    private String username;
    private String email;

    public String getUsername() {
      return username;
    }

    public String getEmail() {
      return email;
    }
  }
}
