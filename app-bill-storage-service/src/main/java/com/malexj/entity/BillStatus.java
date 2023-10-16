package com.malexj.entity;

import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Entity
@QueryEntity
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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Bill bill;
}
