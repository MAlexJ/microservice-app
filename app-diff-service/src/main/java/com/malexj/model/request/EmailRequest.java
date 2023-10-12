package com.malexj.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailRequest {
    @JsonProperty("toEmail")
    private String toEmail;

    @JsonProperty("title")
    private String title;

    @JsonProperty("message")
    private String message;
}
