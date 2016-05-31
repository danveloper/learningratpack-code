package app;

import ratpack.handling.Context;
import ratpack.registry.Registry;
import ratpack.server.RatpackServer;

public class Main {

  private static final String AUTH_HEADER = "x-auth-token";

  public static void main(String[] args) throws Exception {
    RatpackServer.start(spec -> spec
            .registryOf(r -> r
              .add(UserService.class, new DefaultUserService())
            )
            .handlers(chain -> chain
              .all(ctx -> { // <1>
                if (ctx.getRequest().getHeaders().contains(AUTH_HEADER)) { // <2>
                  String token = ctx.getRequest().getHeaders().get(AUTH_HEADER);
                  UserService userService = ctx.get(UserService.class);
                  userService.getProfileByToken(token).then(profile -> // <3>
                    ctx.next(Registry.single(profile)) // <4>
                  );
                } else {
                  unauthorized(ctx);
                }
              })
              .get("users/:username", ctx -> {
                UserProfile profile = ctx.get(UserProfile.class); // <5>
                if (profile.isAuthorized("showuser")) { // <6>
                  UserService userService = ctx.get(UserService.class);
                  userService.getUser(ctx.getPathTokens().get("username"))
                      .then(user -> {
                        ctx.getResponse().contentType("application/json");
                        ctx.render(jsonify(user));
                      });
                } else {
                  unauthorized(ctx);
                }
              })
            )
    );
  }

  private static void unauthorized(Context ctx) {
    ctx.getResponse().status(401);
    ctx.getResponse().send();
  }

  private static String jsonify(User user) {
    return "{ \"username\": \""
        + user.getUsername() + "\", \"email\": \""
        + user.getEmail() + "\" }";
  }
}
