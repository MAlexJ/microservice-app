package com.malexj.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BillRequest {

    @JsonProperty("link")
    private String link;

    @JsonProperty("name")
    private String name;

    @JsonProperty("number")
    private String number;

    @JsonProperty("registrationDate")
    private LocalDate registrationDate;

    @JsonProperty("statuses")
    private List<BillStatus> statuses;

    @Data
    public static class BillStatus {
        @JsonProperty("data")
        private LocalDate data;

        @JsonProperty("status")
        private String status;
    }
}
