package com.game.snakeeyes.IntegrationTest;

import com.game.snakeeyes.client.GetRandomNumbersClient;
import com.game.snakeeyes.exception.SnakeEyesException;
import com.game.snakeeyes.model.RandomNumbersResponse;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Import(EmbeddedMongoAutoConfiguration.class)
class IntegrationTest {

  // TODO add get and update balance calls during testing flow to check balance is being updated
  // correctly

  @Autowired private MockMvc mvc;

  @MockBean private GetRandomNumbersClient client;

  @Test
  void shouldReturnPlayResponseForEachScenario() throws Exception {

    mockClient(1, 1);
    callPlaySnakeEyes(PLAY_RESPONSE_SNAKE_EYES);
    mockClient(3, 3);
    callPlaySnakeEyes(PLAY_RESPONSE_YOU_WIN);
    mockClient(1, 3);
    callPlaySnakeEyes(PLAY_RESPONSE_YOU_LOSE);
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
}
