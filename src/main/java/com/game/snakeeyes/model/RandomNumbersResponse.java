package com.game.snakeeyes.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RandomNumbersResponse {
  private int dice1;
  private int dice2;
}
