package com.game.snakeeyes.service;

import com.game.snakeeyes.client.GetRandomNumbersClient;
import com.game.snakeeyes.exception.InternalException;
import com.game.snakeeyes.exception.SnakeEyesException;
import com.game.snakeeyes.model.PlayResponse;
import com.game.snakeeyes.model.RandomNumbersResponse;
import com.game.snakeeyes.mongodb.BalanceDocument;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayService {

  private MongoDBInteractionService mongoDBInteractionService;
  private GetRandomNumbersClient client;

  public PlayService(
      MongoDBInteractionService mongoDBInteractionService, GetRandomNumbersClient client) {
    this.mongoDBInteractionService = mongoDBInteractionService;
    this.client = client;
  }

  public PlayResponse getPlayResponse(double stake) throws SnakeEyesException, InternalException {

    BalanceDocument balanceDocument = mongoDBInteractionService.getCurrentBalance();

    if (stake > balanceDocument.getBalance()) {
      log.error("stale entered: {} is greater than the users balance", stake);
      throw new SnakeEyesException(
          "You don't have enough in your balance to play with this stake, either play with a lower stake or add more money to your balance");
    }

    RandomNumbersResponse randomNumbers = client.getRandomNumbers();

    int dice1 = randomNumbers.getDice1();
    int dice2 = randomNumbers.getDice2();
    double winnings = 0;
    String payoutName = "you lose";

    if ((dice1 == dice2) && dice2 == 1) {
      winnings = stake * 30;
      payoutName = "snake eyes";
    }

    if ((dice1 == dice2) && dice2 != 1) {
      winnings = stake * 7;
      payoutName = "you win";
    }

    updateNewBalance(stake, balanceDocument, winnings);

    return PlayResponse.builder()
        .dice1(dice1)
        .dice2(dice2)
        .payoutName(payoutName)
        .stake(stake)
        .winnings(winnings)
        .build();
  }

  private void updateNewBalance(double stake, BalanceDocument balanceDocument, double winnings) {
    double currentBalance = balanceDocument.getBalance();
    double newBalance = currentBalance + winnings - stake;
    mongoDBInteractionService.saveBalanceDocument(balanceDocument, newBalance);
  }
}
