package com.malexj.model.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {
    private Long roleId;
    private String email;
    private String username;
    private String password;
    private LocalDate registrationDate;
}
