package app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import ratpack.config.ConfigSource
import ratpack.file.FileSystemBinding

class CustomConfigSource implements ConfigSource { // <1>

  @Override
  ObjectNode loadConfigData(ObjectMapper objectMapper, 
                            FileSystemBinding fileSystemBinding) 
                            throws Exception { // <2>
    ObjectNode node = objectMapper.createObjectNode() // <3>
    DatabaseConfig databaseConfig = new DatabaseConfig(  // <4>
      host: "my-database-host.dev.company.com"
    )
    LandingPageConfig landingPageConfig = new LandingPageConfig( // <5>
      analyticsKey: "ua-learningratpack"
    )

    node.set("database", objectMapper.valueToTree(databaseConfig)) // <6>
    node.set("landing", objectMapper.valueToTree(landingPageConfig)) // <7>

    node // <8>
  }
}
