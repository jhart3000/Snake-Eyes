package com.game.snakeeyes.IntegrationTest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static com.game.snakeeyes.helper.JsonHelper.loadJsonFile;
import static com.game.snakeeyes.helper.TestDataHelper.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Import(EmbeddedMongoAutoConfiguration.class)
class IntegrationTest {

  @Autowired private MockMvc mvc;

  @MockBean private RestTemplate restTemplate;

  @Test
  void shouldReturnPlayResponseForEachScenario() throws Exception {

    mockClient("1", "1");
    callPlaySnakeEyes(PLAY_RESPONSE_SNAKE_EYES);
    callGetCurrentBalance(1029.0);
    mockClient("3", "3");
    callPlaySnakeEyes(PLAY_RESPONSE_YOU_WIN);
    callGetCurrentBalance(1035.0);
    mockClient("1", "3");
    callPlaySnakeEyes(PLAY_RESPONSE_YOU_LOSE);
    callGetCurrentBalance(1034.0);
    callAddToBalance();
    callGetCurrentBalance(1084.0);
  }

  private void mockClient(String dice1, String dice2) {
    String clientBody = dice1 + "\t" + dice2 + "\n";
    Mockito.when(restTemplate.getForEntity(RANDOM_NUMBERS_URL, String.class))
        .thenReturn(new ResponseEntity<>(clientBody, HttpStatus.OK));
  }

  private void callPlaySnakeEyes(String responsePath) throws Exception {
    mvc.perform(get(PLAY_URL))
        .andExpect(status().isOk())
        .andExpect(content().json(loadJsonFile(responsePath)));
  }

  private void callGetCurrentBalance(double expectedBalance) throws Exception {
    mvc.perform(get(GET_BALANCE_URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath(BALANCE_QUERY, Matchers.is(expectedBalance)));
  }

  private void callAddToBalance() throws Exception {
    mvc.perform(put("/addToBalance?amountToAdd=50"))
        .andExpect(status().isOk())
        .andExpect(jsonPath(MESSAGE_QUERY, Matchers.is(BALANCE_UPDATED)));
  }
}
