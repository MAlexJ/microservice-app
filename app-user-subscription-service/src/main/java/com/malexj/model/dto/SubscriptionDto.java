package com.malexj.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SubscriptionDto {

    @JsonProperty("active")
    private boolean isActive;

    @JsonProperty("user")
    private UserDto user;

    @JsonProperty("bills")
    private List<BillDto> bills;
}
