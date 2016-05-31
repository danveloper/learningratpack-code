package tld.company.app;

import ratpack.server.RatpackServer;

public class Main {

  public static void main(String[] args) throws Exception {
    RatpackServer.start(spec -> spec
      .handlers(chain -> chain
        .get(ctx -> ctx.render("Hello, World!"))
      )
    );
  }
}
