package com.malexj.mapper;

import com.malexj.model.dto.request.UserRequest;
import com.malexj.model.dto.response.UserResponse;
import com.malexj.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper:
 */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User convertToEntity(UserRequest request);

    UserResponse convertToResponse(User user);
}
