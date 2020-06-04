package com.game.snakeeyes.service;

import com.game.snakeeyes.mongodb.BalanceDocument;
import com.game.snakeeyes.mongodb.BalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import static com.game.snakeeyes.helper.TestDataHelper.EXPECTED_BALANCE_DOCUMENT;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class MongoDBInteractionServiceTest {

  private MongoDBInteractionService service;

  @Mock BalanceRepository balanceRepository;

  @BeforeEach
  void setUp() {
    service = new MongoDBInteractionService(balanceRepository);
  }

  @Test
  void shouldReturnBalanceDocumentFromMongoDB() {
    given(balanceRepository.findAll())
        .willReturn(Flux.fromIterable(singletonList(EXPECTED_BALANCE_DOCUMENT)));
    BalanceDocument actualBalanceDocument = service.getCurrentBalance();
    assertThat(actualBalanceDocument).isEqualTo(EXPECTED_BALANCE_DOCUMENT);
  }

  @Test
  void shouldBuildNewBalanceDocumentIfMongoDBIsNull() {
    given(balanceRepository.findAll()).willReturn(Flux.fromIterable(emptyList()));
    BalanceDocument actualBalanceDocument = service.getCurrentBalance();
    assertThat(actualBalanceDocument).isEqualTo(EXPECTED_BALANCE_DOCUMENT);
  }
}
