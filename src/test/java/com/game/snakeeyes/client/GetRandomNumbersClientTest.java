package com.game.snakeeyes.client;

import com.game.snakeeyes.exception.ClientException;
import com.game.snakeeyes.model.RandomNumbersResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.game.snakeeyes.helper.TestDataHelper.RANDOM_NUMBERS_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class GetRandomNumbersClientTest {

  @MockBean private RestTemplate restTemplate;

  @Autowired private GetRandomNumbersClient client;

  @Test
  void shouldReturnSuccessfulClientResponse() throws ClientException {
    String clientBody = "6\t3\n";
    Mockito.when(restTemplate.getForEntity(RANDOM_NUMBERS_URL, String.class))
        .thenReturn(new ResponseEntity<>(clientBody, HttpStatus.OK));
    RandomNumbersResponse expectedResponse =
        RandomNumbersResponse.builder().dice1(6).dice2(3).build();
    RandomNumbersResponse actualResponse = client.getRandomNumbers();
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void shouldReturnClientExceptionWhenClientReturnsException() {
    Mockito.when(restTemplate.getForEntity(RANDOM_NUMBERS_URL, String.class))
        .thenThrow(new RuntimeException("error getting random numbers"));
    Throwable errorResponse = catchThrowable(() -> client.getRandomNumbers());
    assertThat(errorResponse)
        .hasMessage(
            "Random numbers client failed with error message: error getting random numbers");
    assertThat(errorResponse).isInstanceOf(ClientException.class);
  }
}
