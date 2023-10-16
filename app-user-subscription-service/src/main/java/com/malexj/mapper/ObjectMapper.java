package com.malexj.mapper;

import com.malexj.model.api.SubscriptionRequest;
import com.malexj.model.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper:
 */
@Mapper(componentModel = "spring")
public interface ObjectMapper {


    @Mapping(target = "id", ignore = true)
    Subscription convertToEntity(SubscriptionRequest request);

}