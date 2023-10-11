package com.serjlemast.mapper;

import com.serjlemast.model.request.EmailRequest;
import com.serjlemast.model.response.EmailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper:
 */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

    @Mapping(target = "request.status", ignore = true)
    EmailResponse convertToResponse(EmailRequest request);
}
