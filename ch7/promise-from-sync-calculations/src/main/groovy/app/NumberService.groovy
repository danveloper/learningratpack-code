package app

import ratpack.exec.Promise

interface NumberService {
  
  Promise<Integer> getRandomNumber()
}
