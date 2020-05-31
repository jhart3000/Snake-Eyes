package com.game.snakeeyes.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = MessageResponse.Builder.class)
public class MessageResponse {

  String message;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {}
}
