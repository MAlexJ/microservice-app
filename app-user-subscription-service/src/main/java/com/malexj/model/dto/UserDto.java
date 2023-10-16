package com.malexj.model.api;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRequest {

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;
}
