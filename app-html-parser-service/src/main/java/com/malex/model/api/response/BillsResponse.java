package com.malex.model.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.malex.model.base.Bill;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillsResponse {

  @JsonProperty("bills")
  private List<Bill> bills;
}
