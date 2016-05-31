This example demonstrates doing the same stuff as `async-with-callbacks`, but this time using `Promise` types. Note that since we're using `Promise` types, we are leveraging the execution model, and thus Ratpack is supervising the async operations for us.

This project can be demonstrated by running with `./gradlew run` and issuing some cURL calls:

 * `curl localhost:5050/ratpack`
 * `curl localhost:5050/profile/ratpack`
 * `curl localhost:5050/photos/ratpack`
