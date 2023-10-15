package com.malexj.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;


@Data
@EqualsAndHashCode(exclude = {"billId"})
public class BillStatus {

    @JsonProperty("bill")
    private String billLink;

    @JsonProperty("data")
    private LocalDate data;

    @JsonProperty("status")
    private String status;



    public BillStatus addBillLink(String link) {
        this.billLink = link;
        return this;
    }

    @Override
    public String toString() {
        return String.format("{data=%s, status=%s}", data, status);
    }
}
