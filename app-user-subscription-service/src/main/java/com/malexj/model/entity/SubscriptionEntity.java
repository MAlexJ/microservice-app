package com.malexj.model.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "subscription")
public class SubscriptionEntity {

    @MongoId
    private String id;

    @Field(name = "active")
    private boolean isActive;

    private UserEntity user;

    private List<BillEntity> bills;

    @CreatedDate
    private LocalDateTime createdDate;

}