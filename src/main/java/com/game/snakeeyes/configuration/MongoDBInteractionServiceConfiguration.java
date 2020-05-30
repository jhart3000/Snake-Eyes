package com.game.snakeeyes.configuration;

import com.game.snakeeyes.mongodb.BalanceRepository;
import com.game.snakeeyes.service.MongoDBInteractionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDBInteractionServiceConfiguration {
  @Bean
  MongoDBInteractionService mongoDBInterationsService(BalanceRepository balanceRepository) {
    return new MongoDBInteractionService(balanceRepository);
  }
}
