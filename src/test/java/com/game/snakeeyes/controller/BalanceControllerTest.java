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

import static com.game.snakeeyes.helper.TestDataHelper.*;
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
    given(service.addMoneyToBalance(FIVE_HUNDRED))
        .willReturn(MessageResponse.builder().message(BALANCE_UPDATED).build());
    mvc.perform(put("/addToBalance?amountToAdd=500.0"))
        .andExpect(status().isOk())
        .andExpect(jsonPath(MESSAGE_QUERY, Matchers.is(BALANCE_UPDATED)));
  }

  @Test
  void shouldReturnSuccessfulBalanceResponse() throws Exception {
    given(service.getCurrentBalance())
        .willReturn(BalanceResponse.builder().currentBalance(FIVE_HUNDRED).build());
    mvc.perform(get(GET_BALANCE_URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath(BALANCE_QUERY, Matchers.is(500.0)));
  }

  @Test
  void shouldReturnBadRequestWhenAddingToBalance() throws Exception {
    mvc.perform(put("/addToBalance?amountToAdd=fifty")).andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnSnakeEyesExceptionWhenAmountToAddIsNegative() throws Exception {
    mvc.perform(put("/addToBalance?amountToAdd=-50"))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath(
                MESSAGE_QUERY, Matchers.is("the amount to add to balance cannot be negative")));
  }

  @Test
  void shouldReturnSnakeEyesExceptionWhenAmountToAddHasMoreThanTwoDecimalPlaces() throws Exception {
    mvc.perform(put("/addToBalance?amountToAdd=50.123"))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath(
                MESSAGE_QUERY,
                Matchers.is(
                    "the amount to add to balance cannot have more than two decimal places")));
  }
}
