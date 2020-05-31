package com.game.snakeeyes.service;

import com.game.snakeeyes.model.BalanceResponse;
import com.game.snakeeyes.model.MessageResponse;
import com.game.snakeeyes.mongodb.BalanceDocument;

public class BalanceService {

  private MongoDBInteractionService mongoDBInteractionService;

  public BalanceService(MongoDBInteractionService mongoDBInteractionService) {
    this.mongoDBInteractionService = mongoDBInteractionService;
  }

  public BalanceResponse getCurrentBalance() {
    BalanceDocument balanceDocument = getCurrentBalanceFromMongoDB();
    return BalanceResponse.builder().currentBalance(balanceDocument.getBalance()).build();
  }

  public MessageResponse addMoneyToBalance(double amountToAdd) {
    BalanceDocument balanceDocument = getCurrentBalanceFromMongoDB();
    updateBalance(balanceDocument, amountToAdd);
    return MessageResponse.builder().message("Balance successfully updated").build();
  }

  private BalanceDocument getCurrentBalanceFromMongoDB() {
    return mongoDBInteractionService.getCurrentBalance();
  }

  private void updateBalance(BalanceDocument balanceDocument, double amountToAdd) {
    double currentBalance = balanceDocument.getBalance();
    double newBalance = currentBalance + amountToAdd;
    mongoDBInteractionService.saveBalanceDocument(balanceDocument, newBalance);
  }
}
