package com.serjlemast.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;


@Data
public class EmailResponse {

    @JsonProperty("toEmail")
    private String toEmail;

    @JsonProperty("title")
    private String title;

    @JsonProperty("message")
    private String message;

    @JsonProperty("registrationDate")
    private LocalDate registrationDate;

    @JsonProperty("status")
    private String status;    //todo

}
