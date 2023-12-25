package com.malex.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Data;

/**
 * Json API request example:
 *
 * <pre>
 *     <code>
 * {
 *         "link":"https://itd.rada.gov.ua/billInfo/Bills/Card/42664",
 *         "number":"9672",
 *          "name":"Name of bill",
 *          "registrationDate":"2023-09-04"
 *       }
 *     </code>
 * </pre>
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
