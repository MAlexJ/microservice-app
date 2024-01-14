package com.malex.model.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Data;

/**
 * Json API request example:
 *
 * <pre>
 *     <code>
 * {
 *         "url":"https://itd.rada.gov.ua/billInfo/Bills/Card/42664",
 *         "number":"9672",
 *          "name":"Name of bill",
 *          "registrationDate":"2023-09-04"
 *       }
 *     </code>
 * </pre>
 */
@Data
public class BillStatusesRequest {

  /** Url contains bill number and link to resources */
  @JsonProperty("url")
  private String url;

  @JsonProperty("name")
  private String name;

  @JsonProperty("number")
  private String number;

  @JsonProperty("registrationDate")
  private LocalDate registrationDate;
}
