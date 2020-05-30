package com.game.snakeeyes.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = PlayResponse.Builder.class)
public class PlayResponse {
  int dice1;
  int dice2;
  double stake;
  double winnings;
  String payoutName;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {}
}
