package com.game.snakeeyes.helper;

import com.game.snakeeyes.mongodb.BalanceDocument;

public class TestDataHelper {
  public static final String PLAY_RESPONSE_SNAKE_EYES = "responses/play-response-snake-eyes.json";
  public static final String PLAY_RESPONSE_YOU_WIN = "responses/play-response-you-win.json";
  public static final String PLAY_RESPONSE_YOU_LOSE = "responses/play-response-you-lose.json";
  public static final String RANDOM_NUMBERS_URL =
      "https://www.random.org/integers?num=2&min=1&max=6&col=2&base=10&format=plain";
  public static final BalanceDocument EXPECTED_BALANCE_DOCUMENT =
      BalanceDocument.builder().balanceId(1234).balance(1000.0).build();
  public static final int FIVE_HUNDRED = 500;
  public static final double ONE_DOUBLE = 1.0;
  public static final String MESSAGE_QUERY = "$.message";
  public static final String BALANCE_QUERY = "$.currentBalance";
  public static final String GET_BALANCE_URL = "/getBalance";
  public static final String PLAY_URL = "/play?stake=1.0";
  public static final String BALANCE_UPDATED = "Balance successfully updated";
}
