package com.malex.models.response;

import com.malex.models.base.Bill;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResponse {
  private List<Bill> bills;
}
