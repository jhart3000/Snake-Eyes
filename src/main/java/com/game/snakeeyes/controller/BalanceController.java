package com.game.snakeeyes.controller;

import com.game.snakeeyes.model.BalanceResponse;
import com.game.snakeeyes.model.MessageResponse;
import com.game.snakeeyes.service.BalanceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

  @Autowired private BalanceService service;

  @GetMapping("/getBalance")
  @ApiOperation(
      value = "returns current balance",
      notes = "This api will retrieve the current balance from mongoDB and return it to the user")
  public BalanceResponse getCurrentBalance() {
    return service.getCurrentBalance();
  }

  @PutMapping("/addToBalance")
  @ApiOperation(
      value = "add money to balance based on query param",
      notes =
          "this api will take in a double as a query param. "
              + "Then it will retrieve the current balance from mongoDB and add the value of the double to the balance and update the mongoDB document")
  public MessageResponse addMoneyToBalance(
      @ApiParam(
              value = "The amount that the user wants to add to their balance. Must be a double",
              required = true)
          @RequestParam()
          double amountToAdd) {
    return service.addMoneyToBalance(amountToAdd);
  }
}
