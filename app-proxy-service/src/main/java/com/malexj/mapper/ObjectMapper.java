package com.malexj.mapper;

import com.malexj.model.api.RestApiRequest;
import com.malexj.model.api.RestApiResponse;
import com.malexj.model.proxy.ProxyRequest;
import com.malexj.model.proxy.ProxyResponse;
import org.mapstruct.Mapper;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

    ProxyRequest convertRequest(RestApiRequest request);

    RestApiResponse convertResponse(ProxyResponse response);

}
