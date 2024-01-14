package com.malexj.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Data;

@Data
public class BillDto {

  @JsonProperty("url")
  private String url;

  @JsonProperty("name")
  private String name;

  @JsonProperty("number")
  private String number;

  @JsonProperty("registrationDate")
  private LocalDate registrationDate;
}
