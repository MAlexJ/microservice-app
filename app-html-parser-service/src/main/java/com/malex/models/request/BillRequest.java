package com.malex.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * Example:
 * {
 * "link":"https://itd.rada.gov.ua/billInfo/Bills/Card/42664",
 * "number":"9672",
 * "name":"Name of bill",
 * "registrationDate":"2023-09-04"
 * }
 */
@Data
public class BillRequest {

    @JsonProperty("link")
    private String link;

    @JsonProperty("name")
    private String name;

    @JsonProperty("number")
    private String number;

    @JsonProperty("registrationDate")
    private LocalDate registrationDate;
}
