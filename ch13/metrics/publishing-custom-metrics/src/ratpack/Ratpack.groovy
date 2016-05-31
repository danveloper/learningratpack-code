import static ratpack.groovy.Groovy.ratpack

import com.codahale.metrics.MetricRegistry
import ratpack.dropwizard.metrics.DropwizardMetricsModule

ratpack {
  bindings {
    module(DropwizardMetricsModule) { c ->
      c.console()
    }
  }
  handlers {
    get("user") { MetricRegistry metricRegistry -> // <1>
      metricRegistry.counter("myapp.user.hits").inc() // <2>
      render "Hello, World!"
    }
  }
}
