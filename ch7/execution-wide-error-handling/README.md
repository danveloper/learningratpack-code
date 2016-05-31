When running this application, it is intended that you will see stacktraces every minute. That is because the `UserService` cannot resolve the `user-service.internal` hostname. You can play around with the `Execution#onError` method inside the `UserService#run` method to see execution error handling behavior.

