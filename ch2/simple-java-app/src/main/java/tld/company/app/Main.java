package tld.company.app;

import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

public class Main {

  public static void main(String[] args) throws Exception {
    RatpackServer.start(spec -> spec
        .handlers(chain -> chain
          .get(ctx -> ctx.render("Hello, World!"))
        )
    );
  }
}
