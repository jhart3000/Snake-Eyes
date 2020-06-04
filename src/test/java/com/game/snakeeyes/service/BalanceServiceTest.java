package com.game.snakeeyes.service;

import com.game.snakeeyes.model.BalanceResponse;
import com.game.snakeeyes.model.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static com.game.snakeeyes.helper.TestDataHelper.EXPECTED_BALANCE_DOCUMENT;
import static com.game.snakeeyes.helper.TestDataHelper.FIVE_HUNDRED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class BalanceServiceTest {

  private BalanceService service;

  @Mock private MongoDBInteractionService mongoDBInteractionService;

  @BeforeEach
  void setUp() {
    service = new BalanceService(mongoDBInteractionService);
    given(mongoDBInteractionService.getCurrentBalance()).willReturn(EXPECTED_BALANCE_DOCUMENT);
  }

  @Test
  void shouldReturnBalanceResponse() {
    BalanceResponse actualResponse = service.getCurrentBalance();
    BalanceResponse expectedResponse = BalanceResponse.builder().currentBalance(1000.0).build();
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void shouldReturnSuccessfulUpdateMessage() {
    MessageResponse actualResponse = service.addMoneyToBalance(FIVE_HUNDRED);
    MessageResponse expectedResponse =
        MessageResponse.builder().message("Balance successfully updated").build();
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }
}
