package com.serjlemast.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Email extends AbstractModel {
    private String toEmail;
    private String title;
    private String message;
    private LocalDate registrationDate;

}
