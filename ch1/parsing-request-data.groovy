@Grab('io.ratpack:ratpack-groovy:1.3.3')

import static ratpack.groovy.Groovy.ratpack
import ratpack.form.Form

ratpack {
  handlers {
    all {
      byMethod {
        get { // <1>
          response.send "text/html", """\
            <!DOCTYPE html>
            <html>
            <body>
            <form method="POST">
            <div>
            <label for="checked">Check</label>
            <input type="checkbox" id="checked" name="checked">
            </div>
            <div>
            <label for="name">Name</label>
            <input type="text" id="name" name="name">
            </div>
            <div>
            <input type="submit">
            </div>
            </form>
            </body>
            </html>
          """.stripIndent()
        }
        post {
          parse(Form).then { formData -> // <2>
            def msg = formData.checked ? "Thanks for the check!" : 
              "Why didn't you check??"
            response.send "text/html", """\
            <!DOCTYPE html>
            <html>
            <body>
            <h1>Welcome, ${formData.name ?: 'Guest'}!</h1>
            <span>${msg}</span>
            """.stripIndent()
          }
        }
      }
    }
  }
}
