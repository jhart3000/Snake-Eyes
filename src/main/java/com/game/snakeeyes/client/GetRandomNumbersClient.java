package com.game.snakeeyes.client;

import com.game.snakeeyes.exception.ClientException;
import com.game.snakeeyes.model.RandomNumbersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

    ResponseEntity<String> response =
        restTemplate.getForEntity(builder.toUriString(), String.class);

    if (response == null) {
      throw new ClientException("Random numbers client failed and is returning null");
    }
    return convertToRandomNumbersResponse(response.getBody());
  }

  private RandomNumbersResponse convertToRandomNumbersResponse(String responseBody) {
    String[] dice = responseBody.split("[\\W]");
    return RandomNumbersResponse.builder()
        .dice1(Integer.parseInt(dice[0]))
        .dice2(Integer.parseInt(dice[1]))
        .build();
  }
}
