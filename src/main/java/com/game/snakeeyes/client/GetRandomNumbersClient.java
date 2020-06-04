package com.game.snakeeyes.client;

import com.game.snakeeyes.exception.ClientException;
import com.game.snakeeyes.model.RandomNumbersResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

import static java.lang.String.format;

@Slf4j
public class GetRandomNumbersClient {

  @Autowired private RestTemplate restTemplate;

  public RandomNumbersResponse getRandomNumbers() throws ClientException {

    String url = "https://www.random.org/integers";
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("num", 2)
            .queryParam("min", 1)
            .queryParam("max", 6)
            .queryParam("col", 2)
            .queryParam("base", 10)
            .queryParam("format", "plain");

    log.info("attempting to retrieve numbers from random numbers client");
    try {
      ResponseEntity<String> response =
          restTemplate.getForEntity(builder.toUriString(), String.class);

      log.info("successfully retrieved numbers from random numbers client");
      return convertToRandomNumbersResponse(Objects.requireNonNull(response.getBody()));

    } catch (RuntimeException e) {
      log.error("random numbers client has failed");
      throw new ClientException(
          format("Random numbers client failed with error message: %s", e.getMessage()));
    }
  }

  private RandomNumbersResponse convertToRandomNumbersResponse(String responseBody) {
    String[] dice = responseBody.split("[\\W]");
    return RandomNumbersResponse.builder()
        .dice1(Integer.parseInt(dice[0]))
        .dice2(Integer.parseInt(dice[1]))
        .build();
  }
}
