package com.serjlemast.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Example:
 * {
 * "toEmail":"for my dear...",
 * "title":"My title",
 * "message":"My message"
 * }
 */
@Data
public class EmailRequest {

    @JsonProperty("toEmail")
    private String toEmail;

    @JsonProperty("title")
    private String title;

    @JsonProperty("message")
    private String message;
}
