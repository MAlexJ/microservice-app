package com.malex.model.base;

import java.time.LocalDate;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Bill extends AbstractModel {
  private String link;
  private String name;
  private String number;
  private LocalDate registrationDate;

  public static class BillBuilder {
    public BillBuilder registrationDate(String date) {
      this.registrationDate = parseDate(date);
      return this;
    }
  }
}
