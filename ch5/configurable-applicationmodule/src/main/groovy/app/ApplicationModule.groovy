package app

import ratpack.guice.ConfigurableModule
import com.google.inject.Scopes
import com.google.inject.Provides

class ApplicationModule extends ConfigurableModule<ApplicationModule.Config> { // <1>
  
  static class Config { // <2>
    String nodeName
  }

  @Override
  void configure() { // <3>
    bind(UserDAO).to(MySqlUserDAO).in(Scopes.SINGLETON)
  }

  @Provides
  UserService userService(UserDAO dao, Config config) { // <4>
    new DefaultUserService(dao, config.nodeName)
  }
}
