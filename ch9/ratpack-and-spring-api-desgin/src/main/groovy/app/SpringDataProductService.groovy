package app

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import ratpack.exec.Promise
import ratpack.exec.Blocking

@Service // <1>
class SpringDataProductService implements ProductService {
  
  private final ProductRepository repo

  @Autowired
  SpringDataProductService(ProductRepository repo) { // <2>
    this.repo = repo
  }

  @Override
  Promise<Product> get(Long id) {
    Blocking.get { // <3>
      repo.findOne(id)
    }
  }

  @Override
  Promise<List<Product>> list() {
    Blocking.get { // <4>
      repo.findAll()
    }
  }
}
