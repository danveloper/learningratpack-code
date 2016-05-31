package app

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Product {
  @Id
  @GeneratedValue
  Long id

  @Column(nullable = false)
  String name

  @Column(nullable = false)
  String description

  @Column(nullable = false, precision = 7, scale = 2)
  BigDecimal price
}
