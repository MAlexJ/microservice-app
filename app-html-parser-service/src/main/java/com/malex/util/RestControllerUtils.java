package com.malex.util;

import com.malex.mapper.ObjectMapper;
import com.malex.model.api.request.BillStatusesRequest;
import com.malex.model.base.Bill;
import com.malex.model.base.BillStatus;
import com.malex.model.api.response.BillStatusesResponse;
import com.malex.model.api.response.BillsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestControllerUtils {
  private final ObjectMapper mapper;

  public BillsResponse buildSearchResponse(List<Bill> bills) {
    return BillsResponse.builder().bills(bills).build();
  }

  public BillStatusesResponse buildBillResponse(BillStatusesRequest request, List<BillStatus> statuses) {
    BillStatusesResponse billStatusesResponse = mapper.convertToResponse(request);
    billStatusesResponse.setStatuses(statuses);
    return billStatusesResponse;
  }
}
