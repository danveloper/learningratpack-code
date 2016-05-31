package app

interface UserDAO {
  List<User> listUsers()
  User findByUsername(String username)
}
