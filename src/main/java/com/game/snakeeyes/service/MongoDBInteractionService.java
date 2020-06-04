package com.game.snakeeyes.service;

import com.game.snakeeyes.mongodb.BalanceDocument;
import com.game.snakeeyes.mongodb.BalanceRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class MongoDBInteractionService {

  private BalanceRepository balanceRepository;

  public MongoDBInteractionService(BalanceRepository balanceRepository) {
    this.balanceRepository = balanceRepository;
  }

  BalanceDocument getCurrentBalance() {
    try {
      BalanceDocument balanceDocument = balanceRepository.findAll().blockFirst();
      log.info("balance retrieved as: {}", Objects.requireNonNull(balanceDocument).getBalance());
      return balanceDocument;

    } catch (RuntimeException e) {
      log.info("initial £1000 balance created because nothing could be retrieved from MongoDB");
      return BalanceDocument.builder().balance(1000.00).balanceId(1234).build();
    }
  }

  void saveBalanceDocument(BalanceDocument balanceDocument, double newBalance) {
    BalanceDocument updatedBalance = balanceDocument.toBuilder().balance(newBalance).build();
    balanceRepository.save(updatedBalance).subscribe();
    log.info("balance successfully updated to: {}", newBalance);
  }
}
