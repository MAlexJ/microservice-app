package com.malex.mapper;

import com.malex.models.request.BillRequest;
import com.malex.models.response.BillResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

  @Mapping(target = "statuses", ignore = true)
  BillResponse convertToResponse(BillRequest request);
}
