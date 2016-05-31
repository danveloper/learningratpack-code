package app

class SecurityConfig {

  BasicAuthConfig basic // <1>

  static class BasicAuthConfig { // <2>
    Map<String, String> userPassMap = [:] // <3>
  }
}