package app

class Person implements RatpackGormEntity<Person> {
  Long id
  Long version
  String name

  static constraints = {
    name blank: false
  }
}
