import ratpack.exec.Promise
import ratpack.health.HealthCheck
import ratpack.health.HealthCheckHandler

import static ratpack.groovy.Groovy.ratpack

class PropertyHolder { // <1>
  boolean healthy = true
}

ratpack {
  bindings {
    bindInstance new PropertyHolder() // <2>
    bindInstance HealthCheck.of("propertyHealth") { registry -> // <3>
      def propertyHolder = registry.get(PropertyHolder)
      Promise.sync {
        propertyHolder.healthy ? // <4>
            HealthCheck.Result.healthy() :
            HealthCheck.Result.unhealthy("the application is unhealthy")
      }
    }
  }
  handlers {
    get("toggleHealth") { PropertyHolder propertyHolder -> // <5>
      propertyHolder.healthy = !propertyHolder.healthy
      response.send()
    }
    get("health/:name?", new HealthCheckHandler()) // <6>
  }
}
