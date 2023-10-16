package com.malexj.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class User {

    private String username;

    @Indexed(unique = true)
    private String email;

}