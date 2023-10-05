package com.malexj.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BillResponse {

    @JsonProperty("_embedded")
    private Embedded embedded;

    @JsonProperty("page")
    private Page page;

    @Data
    public static class Page {
        @JsonProperty("size")
        private Integer size;

        @JsonProperty("totalElements")
        private Integer totalElements;

        @JsonProperty("totalPages")
        private Integer totalPages;

        @JsonProperty("number")
        private String number;
    }

    @Data
    public static class Embedded {
        @JsonProperty("bills")
        private List<Bill> bills;
    }

    @Data
    public static class Bill {
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
    }

    @Data
    public static class BillStatus {
        @JsonProperty("data")
        private LocalDate data;

        @JsonProperty("status")
        private String status;
    }
}
