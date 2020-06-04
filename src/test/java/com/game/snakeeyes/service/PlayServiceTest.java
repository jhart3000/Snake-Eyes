package com.game.snakeeyes.service;

import com.game.snakeeyes.client.GetRandomNumbersClient;
import com.game.snakeeyes.exception.ClientException;
import com.game.snakeeyes.exception.SnakeEyesException;
import com.game.snakeeyes.model.PlayResponse;
import com.game.snakeeyes.model.RandomNumbersResponse;
import com.game.snakeeyes.mongodb.BalanceDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static com.game.snakeeyes.helper.JsonHelper.mapJsonFileToObject;
import static com.game.snakeeyes.helper.TestDataHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class PlayServiceTest {

  private static final int ONE_THOUSAND = 1000;
  private static final int ONE = 1;
  private static final int THREE = 3;

  private PlayService service;

  @Mock private MongoDBInteractionService mongoDBInteractionService;

  @Mock private GetRandomNumbersClient client;

  @BeforeEach
  void setUp() {
    service = new PlayService(mongoDBInteractionService, client);
  }

  @Test
  void shouldGetSnakeEyesPlayResponse() throws Exception {
    mockClient(ONE, ONE);
    mockMongoDBService(ONE_THOUSAND);
    PlayResponse actualResponse = service.getPlayResponse(ONE_DOUBLE);
    PlayResponse expectedResponse =
        mapJsonFileToObject(PLAY_RESPONSE_SNAKE_EYES, PlayResponse.class);
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void shouldGetYouWinPlayResponse() throws Exception {
    mockClient(THREE, THREE);
    mockMongoDBService(ONE_THOUSAND);
    PlayResponse actualResponse = service.getPlayResponse(ONE_DOUBLE);
    PlayResponse expectedResponse = mapJsonFileToObject(PLAY_RESPONSE_YOU_WIN, PlayResponse.class);
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void shouldGetYouWinLoseResponse() throws Exception {
    mockClient(ONE, THREE);
    mockMongoDBService(ONE_THOUSAND);
    PlayResponse actualResponse = service.getPlayResponse(ONE_DOUBLE);
    PlayResponse expectedResponse = mapJsonFileToObject(PLAY_RESPONSE_YOU_LOSE, PlayResponse.class);
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void shouldReturnLowBalanceException() {
    mockMongoDBService(5);
    Throwable errorResponse = catchThrowable(() -> service.getPlayResponse(10.0));
    assertThat(errorResponse)
        .hasMessage(
            "You don't have enough in your balance to play with this stake, either play with a lower stake or add more money to your balance");
    assertThat(errorResponse).isInstanceOf(SnakeEyesException.class);
  }

  private void mockClient(int dice1, int dice2) throws ClientException {
    given(client.getRandomNumbers())
        .willReturn(RandomNumbersResponse.builder().dice1(dice1).dice2(dice2).build());
  }

  private void mockMongoDBService(int balance) {
    given(mongoDBInteractionService.getCurrentBalance())
        .willReturn(BalanceDocument.builder().balance(balance).balanceId(1234).build());
  }
}
