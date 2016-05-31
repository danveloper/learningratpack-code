import static ratpack.groovy.Groovy.ratpack

import ratpack.dropwizard.metrics.DropwizardMetricsModule

ratpack {
  bindings {
    module(DropwizardMetricsModule) { c ->
      c.jvmMetrics true
      c.jmx() // <1>
      c.console() // <2>
      c.slf4j() // <3>
    }
  }
  handlers {
    get {
      render "Hello, World!"
    }
  }
}
