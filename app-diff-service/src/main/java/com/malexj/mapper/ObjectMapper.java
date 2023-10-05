package com.malexj.mapper;

import com.malexj.model.Bill;
import com.malexj.model.request.BillRequest;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper:
 */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

    Bill convertToResponse(BillRequest request);
}
