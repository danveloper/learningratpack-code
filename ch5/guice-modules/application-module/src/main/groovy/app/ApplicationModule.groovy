package app

import com.google.inject.AbstractModule
import com.google.inject.Scopes

class ApplicationModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(UserDAO).to(MySqlUserDAO).in(Scopes.SINGLETON)
    bind(UserService).to(DefaultUserService).asEagerSingleton()
  }
}
