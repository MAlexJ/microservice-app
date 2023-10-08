package com.malexj.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiffResponse {

    @JsonProperty("message")
    private String message;

}
