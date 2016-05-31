@Grab('io.ratpack:ratpack-groovy:1.3.3')

import static ratpack.groovy.Groovy.ratpack
import static groovy.json.JsonOutput.toJson

class User { // <1>
  String username
  String email
}

def user1 = new User( // <2>
  username: "ratpack",
  email: "ratpack@ratpack.io"
)
def user2 = new User(
  username: "danveloper",
  email: "danielpwoods@gmail.com"
)

def users = [user1, user2] // <3>

ratpack {
  handlers {
    get("users") {
      byContent { // <4>
        html { // <5>
          def usersHtml = users.collect { user ->
            """\
            |<div>
            |<b>Username:</b> ${user.username}
            |<b>Email:</b> ${user.email}
            |</div>
            """.stripMargin()
          }.join()

          render """\
          |<!DOCTYPE html>
          |<html>
          |<head>
          |<title>User List</title>
          |</head>
          |<body>
          |<h1>Users</h1>
          |${usersHtml}
          |</body>
          |</html>
          """.stripMargin()
        }
        json { // <6>
          render toJson(users)
        }
        xml { // <7>
          def xmlStrings = users.collect { user ->
            """\
            |<user>
            |  <username>${user.username}</username>
            |  <email>${user.email}</email>
            |</user>
            """.stripIndent().stripMargin().toString()
          }.join()
          render "<users>\n${xmlStrings}</users>"
        }
      }
    }
  }
}
