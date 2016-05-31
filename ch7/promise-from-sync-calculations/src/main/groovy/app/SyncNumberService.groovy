package app

import java.util.Random
import ratpack.exec.Promise

class SyncNumberService implements NumberService {
  
  Promise<Integer> getRandomNumber() {
    Promise.sync { // <1>
      new Random().nextInt() // <2>
    }
  }
}
