package com.malex.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Getter
@Setter
@ToString
@AllArgsConstructor
public class BillRequest {
    private String link;
    private String name;
    private String number;
    private LocalDate registrationDate;
}
