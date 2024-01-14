package com.malexj.model.entity;

import java.time.LocalDate;
import lombok.Data;

@Data
public class BillEntity {

  private String url;

  private String name;

  private String number;

  private LocalDate registrationDate;
}
