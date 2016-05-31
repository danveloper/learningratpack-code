package app

/**
 * This is just for demo purposes, and doesn't actually call into a MySQL database.
 * Chapter 8 has examples of properly building data driven applications.
 */ 
class MySqlUserDAO implements UserDAO {

  private List<User> demoUsers = [
    new User(username: "danveloper", email: "danielpwoods@gmail.com"),
    new User(username: "ldaley", email: "ld@ldaley.com")
  ]

  List<User> listUsers() {
    // simulate a db call
    sleep 500

    demoUsers
  }
}
