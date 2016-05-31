import static ratpack.groovy.Groovy.ratpack

import app.*

ratpack {
  bindings {
    bind(NumberService, SyncNumberService)
  }
  handlers {
    get { NumberService numberService ->
      numberService.randomNumber.then { n -> render n.toString() }
    }
  }
}
