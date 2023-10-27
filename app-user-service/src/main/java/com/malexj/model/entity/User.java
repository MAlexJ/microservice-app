package com.malexj.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table("users")
@EqualsAndHashCode(exclude = {"id"})
public class User {

    @Id
    private Long id;

    private String username;

    private String password;

    private String email;

    @Column("registration_date")
    private LocalDate registrationDate;

    @Column("role_id")
    private Long roleId;

    @Transient
    private Role role;

    public User addRole(Role role) {
        this.role = role;
        return this;
    }
}