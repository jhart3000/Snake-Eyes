package com.game.snakeeyes.controller;

import com.game.snakeeyes.model.BalanceResponse;
import com.game.snakeeyes.model.MessageResponse;
import com.game.snakeeyes.service.BalanceService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class BalanceControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private BalanceService service;

  @Test
  void shouldReturnSuccessfulMessage() throws Exception {
    given(service.addMoneyToBalance(500.0))
        .willReturn(MessageResponse.builder().message("Balance successfully updated").build());
    mvc.perform(put("/addToBalance?amountToAdd=500.0"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", Matchers.is("Balance successfully updated")));
  }

  @Test
  void shouldReturnSuccessfulBalanceResponse() throws Exception {
    given(service.getCurrentBalance())
        .willReturn(BalanceResponse.builder().currentBalance(500.0).build());
    mvc.perform(get("/getBalance"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.currentBalance", Matchers.is(500.0)));
  }

  @Test
  void shouldReturnBadRequestWhenAddingToBalance() throws Exception {
    mvc.perform(put("/addToBalance?amountToAdd=fifty")).andExpect(status().isBadRequest());
  }
}
