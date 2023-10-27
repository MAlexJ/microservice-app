package com.malexj.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table("users")
public class UserEntity {

    @Id
    private Long id;
    private String username;
    private String email;

    @Column("registration_date")
    private LocalDate registrationDate;

}