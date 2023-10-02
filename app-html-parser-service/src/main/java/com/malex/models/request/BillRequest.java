package com.malex.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

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
