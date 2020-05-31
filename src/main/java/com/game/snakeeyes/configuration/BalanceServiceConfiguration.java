package com.game.snakeeyes.configuration;

import com.game.snakeeyes.service.BalanceService;
import com.game.snakeeyes.service.MongoDBInteractionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BalanceServiceConfiguration {

  @Bean
  BalanceService balanceService(MongoDBInteractionService mongoDBInteractionService) {
    return new BalanceService(mongoDBInteractionService);
  }
}
