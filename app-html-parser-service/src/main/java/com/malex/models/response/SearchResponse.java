package com.malex.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.malex.models.base.Bill;
import com.malex.models.base.ResponseState;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResponse {

  @JsonProperty("bills")
  private List<Bill> bills;

  @JsonProperty("state")
  private ResponseState state;
}
