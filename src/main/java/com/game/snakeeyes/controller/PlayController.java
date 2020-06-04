package com.game.snakeeyes.controller;

import com.game.snakeeyes.exception.InternalException;
import com.game.snakeeyes.exception.SnakeEyesException;
import com.game.snakeeyes.model.PlayResponse;
import com.game.snakeeyes.service.PlayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PlayController {

  @Autowired private PlayService service;

  @GetMapping("/play")
  @Operation(
      summary = "Returns winnings based to two random die rolls",
      description =
          "This api will take in a chosen stake from the user as a query param. "
              + "It will then get 2 random numbers from an external client and based on those generate winnings for the user. "
              + "If two ones are rolled the user gets a winning of 30 times the stake entered. "
              + "If any other double is rolled then the winnings are 7 times the stake entered.")
  public PlayResponse playSnakeEyes(
      @Parameter(
              description =
                  "The stake that the user wishes to play, must be either 1, 2 or 10 pounds",
              required = true)
          @RequestParam()
          double stake)
      throws SnakeEyesException, InternalException {

    if (stake != 1.0 && stake != 2.0 && stake != 10.0) {
      log.error("{} is an invalid stake. The query param can only accept 1.0, 2.0 or 10.0", stake);
      throw new SnakeEyesException("the stake entered must be either 1.0, 2.0 or 10.0");
    }

    return service.getPlayResponse(stake);
  }
}
