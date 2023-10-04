package com.malexj.mapper;

import com.malexj.model.request.BillRequest;
import com.malexj.model.response.BillResponse;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper:
 */
@Mapper(componentModel = "spring")
public interface BilDtoMapper {

    BillRequest responseMapper(BillResponse response);
}
