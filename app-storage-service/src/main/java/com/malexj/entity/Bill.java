package com.malexj.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "bill")
public class Bill {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    private String link;

    private String name;

    private String number;

    @Column(name = "registration_date", columnDefinition = "DATE")
    private LocalDate registrationDate;

    @OneToMany
    @JoinColumn(name = "bill_id")
    private List<BillStatus> statuses;
}
