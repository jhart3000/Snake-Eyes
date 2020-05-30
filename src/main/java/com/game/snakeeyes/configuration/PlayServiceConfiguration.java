package com.game.snakeeyes.configuration;

import com.game.snakeeyes.client.GetRandomNumbersClient;
import com.game.snakeeyes.service.MongoDBInteractionService;
import com.game.snakeeyes.service.PlayService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayServiceConfiguration {
  @Bean
  PlayService playService(
      MongoDBInteractionService mongoDBInteractionService, GetRandomNumbersClient client) {
    return new PlayService(mongoDBInteractionService, client);
  }
}
