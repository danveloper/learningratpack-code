package app

import com.google.common.collect.Maps
import groovy.transform.Immutable

class ViewTracker implements Serializable { // <1>
  private Map<String, View> views = Maps.newConcurrentMap() // <2>

  void increment(String uri) { // <3>
    def count = 1
    if (views.containsKey(uri)) {
      count = views.get(uri).count+1 // <4>
    }
    views[uri] = new View(uri, count) // <5>
  }

  List<View> list() { // <6>
    views.values() as List
  }

  @Immutable
  static class View implements Serializable {
    String uri
    int count
  }
}
