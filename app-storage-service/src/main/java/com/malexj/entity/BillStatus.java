package com.malexj.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "bill_status")
public class BillStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data", columnDefinition = "DATE")
    private LocalDate data;

    private String status;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
}
