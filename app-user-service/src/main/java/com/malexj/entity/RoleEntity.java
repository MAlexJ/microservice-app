package com.malexj.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(value = "roles")
public class RoleEntity {

    @Id
    private Long id;

    private String name;
}