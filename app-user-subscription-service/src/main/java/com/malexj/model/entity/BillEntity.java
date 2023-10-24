package com.malexj.model.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BillEntity {

    private String link;

    private String name;

    private String number;

    private LocalDate registrationDate;
}
