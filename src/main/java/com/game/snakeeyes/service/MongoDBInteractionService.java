package com.game.snakeeyes.service;

import com.game.snakeeyes.mongodb.BalanceDocument;
import com.game.snakeeyes.mongodb.BalanceRepository;

public class MongoDBInteractionService {

  private BalanceRepository balanceRepository;

  public MongoDBInteractionService(BalanceRepository balanceRepository) {
    this.balanceRepository = balanceRepository;
  }

  BalanceDocument getCurrentBalance() {
    BalanceDocument balanceDocument = balanceRepository.findAll().blockFirst();
    if (balanceDocument == null) {
      return BalanceDocument.builder().balance(1000.00).balanceId(1234).build();
    }
    return balanceDocument;
  }

  void saveBalanceDocument(BalanceDocument balanceDocument, double newBalance) {
    BalanceDocument updatedBalance = balanceDocument.toBuilder().balance(newBalance).build();
    balanceRepository.save(updatedBalance).subscribe();
  }
}
