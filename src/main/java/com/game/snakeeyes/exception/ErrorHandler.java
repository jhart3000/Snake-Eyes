package com.game.snakeeyes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(value = SnakeEyesException.class)
  public ResponseEntity<ErrorResponse> handleApplicationException(SnakeEyesException e) {
    ErrorResponse errorResponse = ErrorResponse.builder().message(e.getMessage()).build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(value = ClientException.class)
  public ResponseEntity<ErrorResponse> handleApplicationException(ClientException e) {
    ErrorResponse errorResponse = ErrorResponse.builder().message(e.getMessage()).build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
