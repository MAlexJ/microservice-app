package com.malexj.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BillDto {

    @JsonProperty("number")
    private String number;


    @JsonProperty("advancedSearch")
    private boolean advancedSearch;
}
