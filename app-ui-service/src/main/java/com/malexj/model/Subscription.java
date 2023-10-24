package com.malexj.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Subscription {

    @JsonProperty("active")
    private boolean isActive;

    @JsonProperty("user")
    private User user;

    @JsonProperty("bills")
    private List<Bill> bills;
}
