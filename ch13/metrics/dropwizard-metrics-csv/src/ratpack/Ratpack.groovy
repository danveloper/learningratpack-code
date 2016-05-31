import static ratpack.groovy.Groovy.ratpack

import ratpack.dropwizard.metrics.DropwizardMetricsModule

ratpack {
  bindings {
    module(DropwizardMetricsModule) { c ->
      c.csv { csvConfig -> // <1>
        def reportingDir = new File("metrics")
        reportingDir.mkdir()
        csvConfig.reportDirectory(reportingDir) // <2>
      }
    }
  }
  handlers {
    get {
      render "Hello, World!"
    }
  }
}
