package app

import ratpack.exec.Operation
import ratpack.exec.Promise

interface PersonService {
  /**
   * List all the people in the database
   * @return a promise to a list of {@link Person} models
   */
  Promise<List<Person>> list()

  /**
   * Saves the provided {@link Person} model
   * @param person
   */
  Operation save(Person person)
}
