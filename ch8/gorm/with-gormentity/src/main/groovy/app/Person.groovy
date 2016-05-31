package app

import org.grails.datastore.gorm.GormEntity

class Person implements GormEntity<Person> {
  Long id
  Long version
  String name

  static constraints = {
    name blank: false
  }
}
