package tld.company.app;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServlet extends HttpServlet {

  private static final String DB_HOST = System.getProperty("db.host");

  private Connection getConnection() throws SQLException {
    return DriverManager
      .getConnection("jdbc:mysql://" + DB_HOST + "/db", "root", "");
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    try {
      StringBuffer jb = new StringBuffer();
      String line = null;
      BufferedReader reader = request.getReader();
      while ((line = reader.readLine()) != null) {
        jb.append(line);
      }

      ObjectMapper objectMapper = new ObjectMapper();
      User user = objectMapper.readValue(jb.toString(), User.class);

      Connection c = getConnection();
      PreparedStatement pstmt = c
        .prepareStatement("insert into users (username,email) values (?, ?)");

      pstmt.setString(1, user.getUsername());
      pstmt.setString(2, user.getEmail());
      pstmt.execute();
      pstmt.close();
      c.close();
      response.setStatus(200);
    } catch (Exception e) {
      try {
        response.sendError(500, e.getMessage());
      } catch (Exception e1) {
        throw new RuntimeException("error throwing error", e1);
      }
    }
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    try {
      Connection c = getConnection();
      Statement stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery("select username,email from users");

      List<User> users = new ArrayList<>();
      while (rs.next()) {
        String username = rs.getString(1);
        String email = rs.getString(2);
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        users.add(user);
      }
      rs.close();
      c.close();
      response.setStatus(200);
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.writeValue(response.getOutputStream(), users);
    } catch (Exception e) {
      try {
        response.sendError(500, e.getMessage());
      } catch (Exception e1) {
        throw new RuntimeException("error throwing error", e1);
      }
    }
  }
}
