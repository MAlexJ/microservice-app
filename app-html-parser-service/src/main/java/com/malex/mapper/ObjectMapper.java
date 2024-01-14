package com.malex.mapper;

import com.malex.model.api.request.BillStatusesRequest;
import com.malex.model.api.request.BillsRequest;
import com.malex.model.api.response.BillStatusesResponse;
import com.malex.model.proxy.request.BillStatusesProxyRequest;
import com.malex.model.proxy.request.BillsProxyRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

  @Mapping(target = "statuses", ignore = true)
  BillStatusesResponse convertToResponse(BillStatusesRequest request);

  BillStatusesProxyRequest convertBillRequestToProxyRequest(BillStatusesRequest request);

  BillsProxyRequest convertBillsRequestToProxyRequest(BillsRequest request);
}
