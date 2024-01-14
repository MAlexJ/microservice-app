package com.malexj.mapper;

import com.malexj.model.api.BillStatusRequest;
import com.malexj.model.api.BillsRequest;
import com.malexj.model.api.RestApiResponse;
import com.malexj.model.proxy.BillStatusesProxyRequest;
import com.malexj.model.proxy.BillsProxyRequest;
import com.malexj.model.proxy.ProxyResponse;
import org.mapstruct.Mapper;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

  BillStatusesProxyRequest convertRequest(BillStatusRequest request);

  BillsProxyRequest convertRequest(BillsRequest request);

  RestApiResponse convertResponse(ProxyResponse response);
}
