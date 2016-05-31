import tld.company.app.AppHandler

import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    get(new AppHandler())
  }
}
