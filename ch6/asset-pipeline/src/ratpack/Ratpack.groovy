import asset.pipeline.ratpack.AssetPipelineModule

import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    module(AssetPipelineModule) { c ->
      c.sourcePath "../../../src/assets"
    }
  }
  handlers {
    get {
      redirect("/assets/index.html") // <2>
    }
  }
}
