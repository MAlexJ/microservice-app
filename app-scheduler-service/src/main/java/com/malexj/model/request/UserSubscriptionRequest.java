package com.malexj.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserSubscriptionRequest {

    @JsonProperty("active")
    private boolean isActive;

    @JsonProperty("user")
    private UserRequest user;

    @JsonProperty("bills")
    private List<BillRequest> bills;

    @Data
    public static class UserRequest {
        @JsonProperty("username")
        private String username;

        @JsonProperty("email")
        private String email;
    }

}