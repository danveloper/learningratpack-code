package app;

import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

public class Main {

  public static void main(String[] args) throws Exception {
    RatpackServer.start(spec -> spec
            .serverConfig(c -> c.baseDir(BaseDir.find()).build()) // <1>
            .handlers(chain -> chain
                    .files(files -> files
                            .dir("static").indexFiles("index.html")
                    )
            )
    );
  }
}
