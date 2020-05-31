package com.game.snakeeyes.configuration;

import com.game.snakeeyes.client.GetRandomNumbersClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GetRandomNumbersClientConfiguration {
  @Bean
  GetRandomNumbersClient getRandomNumbersClient() {
    return new GetRandomNumbersClient();
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
