package com.malexj.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "subscription")
public class Subscription {

    @Id
    private String id;

    @Field(name = "isActive")
    private boolean isActive;

    private User user;

    private List<String> billNumbers;

}