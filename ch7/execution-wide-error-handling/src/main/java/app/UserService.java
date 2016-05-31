package app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import ratpack.exec.ExecController;
import ratpack.exec.Execution;
import ratpack.http.client.HttpClient;
import ratpack.service.Service;
import ratpack.service.StartEvent;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UserService implements Service, Runnable { // <1>
  static String USER_SERVICE_URI = "https://user-service.internal";

  private final HttpClient httpClient;
  private final ObjectMapper mapper;

  private final Map<String, User> userCache = Maps.newConcurrentMap();

  @Inject
  public UserService(HttpClient httpClient, ObjectMapper mapper) { // <2>
    this.httpClient = httpClient;
    this.mapper = mapper;
  }

  @Override
  public void onStart(StartEvent event) {
    ExecController execController = event.getRegistry().get(ExecController.class); // <3>
    execController.getExecutor()
        .scheduleAtFixedRate(this, 0, 60, TimeUnit.SECONDS); // <4>
  }

  @Override
  public void run() {
    Execution.fork().onError(t -> { // <1>
      t.printStackTrace();
    }).start(e -> { // <5>
      httpClient.get(new URI(USER_SERVICE_URI+"/users")).map(response -> { // <6>
        return (List<User>)mapper
            .readValue(response.getBody().getBytes(),
                new TypeReference<List<User>>() {}); // <7>
      }).then(users -> { // <8>
        for (User user : users) {
          userCache.put(user.getUsername(), user);
        }
      });
    });
  }

  public User findByUsername(String username) {
    return userCache.containsKey(username) ? userCache.get(username) : null;
  }

  public List<User> list() {
    return Lists.newArrayList(userCache.values());
  }
}
