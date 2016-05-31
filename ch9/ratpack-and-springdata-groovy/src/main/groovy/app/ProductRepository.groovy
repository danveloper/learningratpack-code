package app

import app.Product
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository // <1>
interface ProductRepository extends CrudRepository<Product, Long> { // <2>
}
