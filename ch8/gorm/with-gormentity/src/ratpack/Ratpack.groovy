import app.GormModule
import app.Person
import grails.orm.bootstrap.HibernateDatastoreSpringInitializer
import ratpack.service.Service
import ratpack.service.StartEvent
import ratpack.form.Form
import ratpack.exec.Blocking
import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

ratpack {
  bindings {
    module GormModule

    bindInstance new Service() {
      void onStart(StartEvent e) throws Exception {
        e.getRegistry().get(HibernateDatastoreSpringInitializer)
        Blocking.exec {
          Person.withNewSession {
            new Person(name: "Luke Daley").save()
            new Person(name: "Rob Fletch").save()
            new Person(name: "Dan Woods").save()
          }
        }
      }
    }
  }
  handlers {
    get {
      Blocking.get {
        Person.withNewSession {
          Person.list().collect { p ->
            [id: p.id, version: p.version, name: p.name]
          }
        }
      } then { personList ->
        render(json(personList))
      }
    }
    post("create") {
      parse(Form).then { f -> // <1>
        def name = f.name
        if (name) { // <2>
          Blocking.get { // <3>
            Person.withNewSession { // <4>
              new Person(name: name).save() // <5>
            }
          } onError { t -> // <6>
            response.status(400)
            render(json([success: false, error: t.message]))
          } then {
            render(json([success: true, error: null])) // <7>
          }
        } else { // <8>
          response.status(400)
          render(json([success: false, error: "name not provided"]))
        }
      }
    }
  }
}