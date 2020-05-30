package com.game.snakeeyes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;

@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
public class SnakeEyesApplication {

  // TODO add Docker file, postman collection, detailed Readme, update to swagger 3, add swagger
  // details in playResponse, add sonar qube

  public static void main(String[] args) {
    SpringApplication.run(SnakeEyesApplication.class, args);
  }
}
