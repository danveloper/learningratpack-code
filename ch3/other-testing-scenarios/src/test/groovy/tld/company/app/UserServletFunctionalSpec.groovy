package tld.company.app

import groovy.json.JsonSlurper
import groovy.sql.Sql
import org.apache.catalina.ssi.ByteArrayServletOutputStream
import ratpack.func.Action
import ratpack.test.embed.EmbeddedApp
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.sql.DriverManager

import static groovy.json.JsonOutput.toJson

class UserServletFunctionalSpec extends Specification {
  private static final String DB_HOST = "localhost"
  private static final Integer DB_PORT = 3306
  private static final Sql sql =
      new Sql(
        DriverManager.getConnection(
          "jdbc:mysql://${DB_HOST}:${DB_PORT}/db", 
          "root", 
          ""
        )
      )

  static {
    System.setProperty("db.host", DB_HOST)
    System.setProperty("db.port", DB_PORT.toString())
  }

  UserServlet userServlet = new UserServlet()
  JsonSlurper jsonSlurper = new JsonSlurper()

  EmbeddedApp app = EmbeddedApp.of({ spec -> // <1>
    spec.handlers { chain ->
      chain.all { ctx ->
        def servletRequest = Stub(HttpServletRequest) // <2>
        def servletResponse = Stub(HttpServletResponse) // <3>
        def outputStream = new ByteArrayServletOutputStream()
        def responseStatus = 0
        def bodyPromise = ctx.request.body.map { body ->
          servletRequest.getReader() >> {
            new BufferedReader(body.inputStream.newReader()) // <4>
          }
        }
        servletResponse.setStatus(_) >> { int status -> // <5>
          responseStatus = status
        }
        servletResponse.sendError(_, _) >> { int status, String msg -> // <6>
          responseStatus = status
          outputStream.write(msg.bytes)
        }
        servletResponse.getOutputStream() >> outputStream
        ctx.byMethod { b ->
          b.post({
            bodyPromise.then {
              userServlet.doPost(servletRequest, servletResponse) // <7>
              ctx.response.status(responseStatus) // <8>
              ctx.response.send(outputStream.toByteArray()) // <9>
            }
          })
          b.get({
            bodyPromise.then {
              userServlet.doGet(servletRequest, servletResponse) // <10>
              ctx.response.status(responseStatus) // <11>
              ctx.response.send(outputStream.toByteArray()) // <12>
            }
          })
        }
      }
    }
  } as Action)

  def setupSpec() { // <13>
    trunc()
  }

  def cleanupSpec() { // <14>
    trunc()
  }

  private static void trunc() {
    sql.execute("delete from users")
  }

  void "should save user data and list results"() { // <15>
    setup:
    def user = [username: "danveloper", email: "daniel.p.woods@gmail.com"]

    when:
    def postResponse = app.httpClient.requestSpec { spec ->
      spec.body { b ->
        b.text(toJson(user))
      }
    }.post()

    then:
    postResponse.statusCode == 200

    when:
    def getResponse = app.httpClient.get()

    then:
    getResponse.statusCode == 200
    (jsonSlurper.parseText(getResponse.body.text) as List) == [user]
  }
}
