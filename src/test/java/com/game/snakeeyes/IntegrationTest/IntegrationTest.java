package com.game.snakeeyes.IntegrationTest;

import com.game.snakeeyes.client.GetRandomNumbersClient;
import com.game.snakeeyes.exception.SnakeEyesException;
import com.game.snakeeyes.model.RandomNumbersResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.game.snakeeyes.helper.JsonHelper.loadJsonFile;
import static com.game.snakeeyes.helper.TestDataHelper.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Import(EmbeddedMongoAutoConfiguration.class)
class IntegrationTest {

  @Autowired private MockMvc mvc;

  @MockBean private GetRandomNumbersClient client;

  @Test
  void shouldReturnPlayResponseForEachScenario() throws Exception {

    mockClient(1, 1);
    callPlaySnakeEyes(PLAY_RESPONSE_SNAKE_EYES);
    callGetCurrentBalance(1029.0);
    mockClient(3, 3);
    callPlaySnakeEyes(PLAY_RESPONSE_YOU_WIN);
    callGetCurrentBalance(1035.0);
    mockClient(1, 3);
    callPlaySnakeEyes(PLAY_RESPONSE_YOU_LOSE);
    callGetCurrentBalance(1034.0);
    callAddToBalance();
    callGetCurrentBalance(1084.0);
  }

  private void mockClient(int dice1, int dice2) throws SnakeEyesException {
    given(client.getRandomNumbers())
        .willReturn(RandomNumbersResponse.builder().dice1(dice1).dice2(dice2).build());
  }

  private void callPlaySnakeEyes(String responsePath) throws Exception {
    mvc.perform(get("/play?stake=1.0"))
        .andExpect(status().isOk())
        .andExpect(content().json(loadJsonFile(responsePath)));
  }

  private void callGetCurrentBalance(double expectedBalance) throws Exception {
    mvc.perform(get("/getBalance"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.currentBalance", Matchers.is(expectedBalance)));
  }

  private void callAddToBalance() throws Exception {
    mvc.perform(put("/addToBalance?amountToAdd=50"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", Matchers.is("Balance successfully updated")));
  }
}
