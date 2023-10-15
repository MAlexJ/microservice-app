package com.malexj.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Bill {

    @JsonProperty("id")
    private Long id;

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

    /**
     * Example:
     * "_links":
     * {
     * "self":{
     * "href":"http://localhost:8087/bills/13"
     * },
     * "bill":{
     * "href":"http://localhost:8087/bills/13"
     * }
     * }
     */
    @JsonProperty("_links")
    private Link selfLink;

    @Data
    public static class Link {
        @JsonProperty("self")
        private Self self;
    }

    @Data
    public static class Self {
        @JsonProperty("href")
        private String href;
    }

}
