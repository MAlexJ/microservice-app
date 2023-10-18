package com.malexj.mapper;

import com.malexj.model.dto.SubscriptionDto;
import com.malexj.model.entity.SubscriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper
 */
@Mapper(componentModel = "spring")
public interface ObjectMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    SubscriptionEntity convertDtoToEntity(SubscriptionDto dto);

}