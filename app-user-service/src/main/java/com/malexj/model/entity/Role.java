package com.malexj.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(value = "roles")
@EqualsAndHashCode(exclude = {"id"})
public class Role {

    @Id
    private Long id;

    private String name;
}