package app

import ratpack.service.Service
import ratpack.service.StartEvent
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class AppSpringConfig {

  @Bean
  Service bootstrapDb(ProductRepository repo) { // <1>
    new Service() {
      void onStart(StartEvent e) throws Exception {
        def registry = e.registry // <2>
        def bootstrapConfig = registry.get(ProductBootstrapConfig) // <3>
        repo.save(bootstrapConfig.products) // <4>
      }
    }
  }
}
