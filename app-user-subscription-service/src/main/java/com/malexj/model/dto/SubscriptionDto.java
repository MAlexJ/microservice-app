package com.malexj.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SubscriptionRequest {

    @JsonProperty("active")
    private boolean isActive;

    @JsonProperty("user")
    private UserRequest user;

    @JsonProperty("billNumbers")
    private List<String> billNumbers;
}
