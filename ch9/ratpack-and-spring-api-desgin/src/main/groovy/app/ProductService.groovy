package app

import ratpack.exec.Promise

interface ProductService {
  
  Promise<Product> get(Long id) // <1>

  Promise<List<Product>> list() // <2>
}
