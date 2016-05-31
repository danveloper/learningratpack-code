import static ratpack.groovy.Groovy.ratpack

import ratpack.dropwizard.metrics.DropwizardMetricsModule
import java.util.concurrent.TimeUnit

ratpack {
  bindings {
    module(DropwizardMetricsModule) { c ->
      c.graphite { graphiteConfig -> // <1>
        graphiteConfig.prefix("myapp") // <2>
          .rateUnit(TimeUnit.MILLISECONDS) // <3>
          .durationUnit(TimeUnit.MILLISECONDS) // <4>
      }
    }
  }
  handlers {
    get {
      render "Hello, World!"
    }
  }
}
