package com.game.snakeeyes.service;

import com.game.snakeeyes.mongodb.BalanceDocument;
import com.game.snakeeyes.mongodb.BalanceRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoDBInteractionService {

  private BalanceRepository balanceRepository;

  public MongoDBInteractionService(BalanceRepository balanceRepository) {
    this.balanceRepository = balanceRepository;
  }

  BalanceDocument getCurrentBalance() {
    BalanceDocument balanceDocument = balanceRepository.findAll().blockFirst();
    if (balanceDocument == null) {
      log.info("initial £1000 balance created because mongoDB returned null");
      return BalanceDocument.builder().balance(1000.00).balanceId(1234).build();
    }
    return balanceDocument;
  }

  void saveBalanceDocument(BalanceDocument balanceDocument, double newBalance) {
    BalanceDocument updatedBalance = balanceDocument.toBuilder().balance(newBalance).build();
    balanceRepository.save(updatedBalance).subscribe();
    log.info(
        "balance successfully updated from: {} to: {}", balanceDocument.getBalance(), newBalance);
  }
}
