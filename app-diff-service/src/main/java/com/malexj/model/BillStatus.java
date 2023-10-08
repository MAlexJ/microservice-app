package com.malexj.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;


@Data
public class BillStatus {

    @JsonProperty("data")
    private LocalDate data;

    @JsonProperty("status")
    private String status;

    @Override
    public String toString() {
        return String.format("{data=%s, status=%s}", data, status);
    }
}
