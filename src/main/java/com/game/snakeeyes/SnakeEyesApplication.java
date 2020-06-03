package com.game.snakeeyes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;

@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
public class SnakeEyesApplication {

  public static void main(String[] args) {
    SpringApplication.run(SnakeEyesApplication.class, args);
  }
}
