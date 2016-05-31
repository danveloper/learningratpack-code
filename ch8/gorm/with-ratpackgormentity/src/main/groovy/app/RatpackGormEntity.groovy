package app

import groovy.transform.CompileStatic
import org.grails.datastore.gorm.GormEnhancer
import org.grails.datastore.gorm.GormEntity
import org.grails.datastore.gorm.GormStaticApi
import ratpack.exec.Blocking
import ratpack.exec.Promise

@CompileStatic
trait RatpackGormEntity<D> extends GormEntity<D> {
  private static GormStaticApi<D> internalStaticApi // <1>

  static GormStaticApi<D> currentGormStaticApi() {
    if (internalStaticApi == null) {
      internalStaticApi = (GormStaticApi<D>) GormEnhancer.findStaticApi(this) // <2>
    }
    internalStaticApi
  }

  static <V> Promise<V> withNewSession(Closure callable) { // <3>
    Blocking.get { // <4>
      (V) currentGormStaticApi().withNewSession(callable) // <5>
    }
  }
}
