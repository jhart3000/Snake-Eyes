package com.game.snakeeyes.controller;

import com.game.snakeeyes.model.PlayResponse;
import com.game.snakeeyes.service.PlayService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.game.snakeeyes.helper.JsonHelper.loadJsonFile;
import static com.game.snakeeyes.helper.JsonHelper.mapJsonFileToObject;
import static com.game.snakeeyes.helper.TestDataHelper.PLAY_RESPONSE_SNAKE_EYES;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class PlayControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private PlayService service;

  @Test
  void shouldReturnSuccessfulPlayResponse() throws Exception {

    PlayResponse playResponse = mapJsonFileToObject(PLAY_RESPONSE_SNAKE_EYES, PlayResponse.class);
    given(service.getPlayResponse(1.0)).willReturn(playResponse);
    mvc.perform(get("/play?stake=1.0"))
        .andExpect(status().isOk())
        .andExpect(content().json(loadJsonFile(PLAY_RESPONSE_SNAKE_EYES)));
  }

  @Test
  void shouldReturnSnakeEyesException() throws Exception {

    mvc.perform(get("/play?stake=3.0"))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath(
                "$.message", Matchers.is("the stake entered must be either 1.0, 2.0 or 10.0")));
  }

  @Test
  void shouldReturnBadRequest() throws Exception {

    mvc.perform(get("/play?stake=3")).andExpect(status().isBadRequest());
  }
}