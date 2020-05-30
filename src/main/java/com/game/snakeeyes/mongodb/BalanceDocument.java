package com.game.snakeeyes.mongodb;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
public class BalanceDocument {
  @Id private int balanceId;
  private double balance;
}
