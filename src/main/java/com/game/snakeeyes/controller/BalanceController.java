package com.game.snakeeyes.controller;

import com.game.snakeeyes.exception.SnakeEyesException;
import com.game.snakeeyes.model.BalanceResponse;
import com.game.snakeeyes.model.MessageResponse;
import com.game.snakeeyes.service.BalanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BalanceController {

  @Autowired private BalanceService service;

  @GetMapping("/getBalance")
  @Operation(
      summary = "returns current balance",
      description =
          "This api will retrieve the current balance from mongoDB and return it to the user")
  public BalanceResponse getCurrentBalance() {
    return service.getCurrentBalance();
  }

  @PutMapping("/addToBalance")
  @Operation(
      summary = "add money to balance based on query param",
      description =
          "this api will take in a double as a query param. "
              + "Then it will retrieve the current balance from mongoDB and add the value of the double to the balance and update the mongoDB document")
  public MessageResponse addMoneyToBalance(
      @Parameter(
              description =
                  "The amount that the user wants to add to their balance. Must be a double",
              required = true)
          @RequestParam()
          double amountToAdd) throws SnakeEyesException {
    if(amountToAdd < 0) {
      log.error("amountToAdd value: {} is negative and must be a positive value", amountToAdd);
      throw new SnakeEyesException("the amount to add to balance cannot be negative");
    }
    return service.addMoneyToBalance(amountToAdd);
  }
}
