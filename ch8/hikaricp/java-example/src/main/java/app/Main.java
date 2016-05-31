package app;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ratpack.exec.Blocking;
import ratpack.form.Form;
import ratpack.guice.Guice;
import ratpack.hikari.HikariModule;
import ratpack.server.RatpackServer;
import ratpack.server.Service;
import ratpack.server.StartEvent;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import static ratpack.jackson.Jackson.json;

public class Main {

  public static void main(String[] args) throws Exception {
    RatpackServer.start(spec -> spec
      .registry(Guice.registry(bindingsSpec ->
        bindingsSpec
          .module(HikariModule.class, c -> {
            c.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
            c.addDataSourceProperty("URL", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
              c.setUsername("sa");
              c.setPassword("");
            })
            .bindInstance(new Service() {
              @Override
              public void onStart(StartEvent event) throws Exception {
                DataSource dataSource = event.getRegistry().get(DataSource.class);
                try (Connection connection = dataSource.getConnection()) {
                  connection.createStatement()
                    .execute("CREATE TABLE TEST(ID INT PRIMARY KEY " + 
                             "AUTO_INCREMENT, NAME VARCHAR(255))");
                  connection.createStatement()
                    .execute("INSERT INTO TEST (NAME) VALUES('Luke Daley')");
                  connection.createStatement()
                    .execute("INSERT INTO TEST (NAME) VALUES('Rob Fletch')");
                  connection.createStatement()
                    .execute("INSERT INTO TEST (NAME) VALUES('Dan Woods')");
                }
              }
            })
      ))
      .handlers(chain -> chain
        .get(ctx -> {
          Blocking.get(() -> {
            DataSource dataSource = ctx.get(DataSource.class);
            List<Map<String, String>> personList = Lists.newArrayList();
            try (Connection connection = dataSource.getConnection()) {
              ResultSet rs = connection.createStatement()
                .executeQuery("SELECT * FROM TEST");
              while (rs.next()) {
                long id = rs.getLong(1);
                String name = rs.getString(2);
                Map<String, String> person = Maps.newHashMap();
                person.put("id", String.valueOf(id));
                person.put("name", name);
                personList.add(person);
              }
            }
            return personList;
          }).then(personList -> ctx.render(json(personList)));
        })
        .post("create", ctx ->
          ctx.parse(Form.class).then(f -> {
            String name = f.get("name");
            if (name != null) {
              Blocking.get(() -> {
                DataSource dataSource = ctx.get(DataSource.class);
                try (Connection connection = dataSource.getConnection()) {
                  PreparedStatement pstmt = connection
                    .prepareStatement("INSERT INTO TEST (NAME) VALUES(?)");
                  pstmt.setString(1, name);
                  pstmt.execute();
                }
                return true;
              }).onError(t -> {
                ctx.getResponse().status(400);
                ctx.render(json(getResponseMap(false, t.getMessage())));
              }).then(r ->
                ctx.render(json(getResponseMap(true, null)))
              );
            } else {
              ctx.getResponse().status(400);
              ctx.render(json(getResponseMap(false, "name not provided")));
            }
          })
        )
      )
    );
  }

  private static Map<String, Object> getResponseMap(Boolean status, String message) {
    Map<String, Object> response = Maps.newHashMap();
    response.put("success", status);
    response.put("error", message);
    return response;
  }
}
