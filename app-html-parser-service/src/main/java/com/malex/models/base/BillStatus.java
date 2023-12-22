package com.malex.models.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class BillStatus extends AbstractModel {

  @JsonProperty("data")
  private LocalDate data;

  @JsonProperty("status")
  private String status;

  public static class BillStatusBuilder {
    public BillStatusBuilder data(String date) {
      this.data = parseDate(date);
      return this;
    }
  }
}
