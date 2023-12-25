package com.malex.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.malex.models.base.ResponseState;

public record HtmlParserResponse(
    @JsonProperty("value") String value, @JsonProperty("state") ResponseState state) {}
