package app

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import grails.orm.bootstrap.HibernateDatastoreSpringInitializer
import org.h2.Driver
import org.springframework.context.support.GenericApplicationContext
import org.springframework.jdbc.datasource.DriverManagerDataSource

class GormModule extends AbstractModule { // <1>
  @Override
  protected void configure() {
     // <2>
  }

  @Provides
  @Singleton
  GenericApplicationContext genericApplicationContext() {
    new GenericApplicationContext() // <3>
  }

  @Provides
  @Singleton
  DriverManagerDataSource dataSource(GenericApplicationContext appCtx) { // <4>
    def dataSource =
        new DriverManagerDataSource("jdbc:h2:mem:grailsDb1;DB_CLOSE_DELAY=-1",
            'sa', '') // <5>
    dataSource.driverClassName = Driver.name // <6>
    appCtx.beanFactory.registerSingleton 'dataSource', dataSource // <7>
    dataSource
  }

  @Provides
  @Singleton
  HibernateDatastoreSpringInitializer initializer(DriverManagerDataSource ds,
                                                  GenericApplicationContext appCtx) {
    def datastoreInitializer = new HibernateDatastoreSpringInitializer(Person) // <8>
    datastoreInitializer.configureForBeanDefinitionRegistry(appCtx) // <9>
    appCtx.refresh() // <10>
    datastoreInitializer
  }
}