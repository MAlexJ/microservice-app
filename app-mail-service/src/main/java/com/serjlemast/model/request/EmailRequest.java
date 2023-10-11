package com.serjlemast.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * Example:
 * {
 * "toEmail":"for my dear...",
 * "title":"My title",
 * "message":"My message",
 * "registrationDate":"2023-10-10"
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

    @JsonProperty("registrationDate")
    private LocalDate registrationDate;
}
