This example demonstrates utilizing a blocking (I/O bound) API in an asynchronous way using Ratpack's `Blocking` execution fixture.

This project can be demonstrated by running with `./gradlew run` and issuing some cURL calls:

 * `curl localhost:5050/ratpack`
 * `curl localhost:5050/profile/ratpack`
 * `curl localhost:5050/photos/ratpack`
