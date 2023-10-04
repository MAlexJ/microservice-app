package com.malexj.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SearchResponse {

    @JsonProperty("bills")
    private List<BillResponse> bills;
}
