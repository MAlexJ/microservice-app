package com.malexj.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Data
@Entity
@QueryEntity
@Table(name = "bill")
public class Bill {

    private static final Comparator<BillStatus> BILL_STATUSES_COMPARATOR_BY_DATE_DES //
            = Comparator.comparing(BillStatus::getData).reversed();

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

    @RestResource(exported = false)
    @OneToMany(mappedBy = "bill", targetEntity = BillStatus.class, cascade = CascadeType.ALL)
    private List<BillStatus> statuses;

    public void setStatuses(List<BillStatus> statuses) {
        if (null != statuses) {
            applyBillStatuses(statuses);
        }
        this.statuses = statuses;
    }

    private void applyBillStatuses(List<BillStatus> statuses) {
        statuses.stream() //
                .sorted(BILL_STATUSES_COMPARATOR_BY_DATE_DES) //
                .forEach(status -> status.setBill(this));
    }
}