package com.malex.model.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.malex.model.api.request.BillStatusesRequest;
import com.malex.model.base.BillStatus;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import lombok.Data;

@Data
public class BillStatusesResponse {

  @JsonProperty("url")
  private String url;

  @JsonProperty("name")
  private String name;

  @JsonProperty("number")
  private String number;

  @JsonProperty("registrationDate")
  private LocalDate registrationDate;

  @JsonProperty("statuses")
  private List<BillStatus> statuses;

  public BillStatusesResponse fetchStatuses(List<BillStatus> statuses) {
    this.setStatuses(statuses);
    return this;
  }
}
