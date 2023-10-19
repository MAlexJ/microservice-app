package com.serjlemast.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@QueryEntity
@Table(name = "exception")
public class ExceptionEntity {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Long id;

    @Column(name = "ex_date", columnDefinition = "DATE")
    private LocalDate registrationDate;

    private String message;

    private String type;

    @Column(name = "from_service")
    private String fromService;

}
