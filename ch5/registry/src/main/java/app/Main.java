package app;

import ratpack.server.RatpackServer;

public class Main {

  public static void main(String[] args) throws Exception {
    RatpackServer.start( spec -> spec
        .registryOf(r -> r // <1>
          .add(UserService.class, new DefaultUserService()) // <2>
        )
        .handlers(chain -> chain
                .get(ctx -> {
                  UserService userService = ctx.get(UserService.class); // <3>
                  userService.list().then(users -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append('[');
                    for (User user : users) {
                      sb.append(jsonify(user));
                    }
                    sb.append(']');
                    ctx.getResponse().contentType("application/json");
                    ctx.render(sb.toString());
                  });
                })
        )
    );
  }

  private static String jsonify(User user) {
    return "{ \"username\": \""
        +user.getUsername()+"\", \"email\": \""
        +user.getEmail()+"\" }";
  }
}
