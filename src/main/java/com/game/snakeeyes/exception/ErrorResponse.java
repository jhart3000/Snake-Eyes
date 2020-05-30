package com.game.snakeeyes.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class ErrorResponse {
  private String message;
}
