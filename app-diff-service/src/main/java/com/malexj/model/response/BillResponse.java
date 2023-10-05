package com.malexj.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.malexj.model.Bill;
import lombok.Data;

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

}
