import app.GormModule
import app.Person
import grails.orm.bootstrap.HibernateDatastoreSpringInitializer
import ratpack.form.Form
import ratpack.service.Service
import ratpack.service.StartEvent

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

ratpack {
  bindings {
    module GormModule

    bindInstance new Service() {
      void onStart(StartEvent e) throws Exception {
        e.getRegistry().get(HibernateDatastoreSpringInitializer)
        Person.withNewSession {
          new Person(name: "Luke Daley").save()
          new Person(name: "Rob Fletch").save()
          new Person(name: "Dan Woods").save()
        } operation() then()
      }
    }
  }
  handlers {
    get {
      Person.withNewSession {
        Person.list().collect { p ->
          [id: p.id, version: p.version, name: p.name]
        }
      } then { personList ->
        render(json(personList))
      }
    }
    post("create") {
      parse(Form).then { f ->
        def name = f.name
        if (name) {
          Person.withNewSession {
            new Person(name: name).save()
          } onError { t ->
            response.status(400)
            render(json([success: false, error: t.message]))
          } then { person ->
            render(json([success: true, error: null]))
          }
        } else {
          response.status(400)
          render(json([success: false, error: "name not provided"]))
        }
      }
    }
  }
}
