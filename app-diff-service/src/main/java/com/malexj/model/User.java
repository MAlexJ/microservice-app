package com.malexj.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;
}
